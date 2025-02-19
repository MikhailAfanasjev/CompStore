package com.example.compstore.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compstore.modelDB.CartItem
import com.example.compstore.modelDB.Order
import com.example.compstore.repository.CartItemRepository
import com.example.compstore.repository.OrderRepository
import com.example.compstore.repository.UserRepository
import com.example.compstore.ui.screen.PCComponentsScreens.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
    private val cartItemRepository: CartItemRepository
) : ViewModel() {

    private val _userId = MutableStateFlow<Int?>(null)
    val userId: StateFlow<Int?> get() = _userId

    // Поток заказов пользователя (сбор данных начинается сразу)
    val orders: StateFlow<List<Order>> = _userId
        .filterNotNull()
        .flatMapLatest { userId ->
            Log.d("OrderHistoryViewModel", "Получение заказов для пользователя с id: $userId")
            orderRepository.getOrdersByUserIdFlow(userId)
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // Поток элементов корзины пользователя (сбор данных начинается сразу)
    val cartItems: StateFlow<List<CartItem>> = _userId
        .filterNotNull()
        .flatMapLatest { userId ->
            Log.d("OrderHistoryViewModel", "Получение элементов корзины для пользователя с id: $userId")
            cartItemRepository.getCartItemsByUserIdFlow(userId)
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        viewModelScope.launch {
            userRepository.getLoggedInUser().collectLatest { user ->
                if (user != null) {
                    Log.d("OrderHistoryViewModel", "Пользователь авторизовался: id = ${user.id}")
                } else {
                    Log.d("OrderHistoryViewModel", "Пользователь вышел из системы или не авторизован")
                }
                _userId.value = user?.id
            }
        }
    }

    fun completePurchase(allProducts: List<Product>) {
        val currentUserId = _userId.value
        if (currentUserId != null) {
            viewModelScope.launch {
                Log.d("OrderHistoryViewModel", "Начало оформления покупки для пользователя с id: $currentUserId")

// Собираем корзину для авторизованного пользователя
                val basketItems = cartItems.value.mapNotNull { cartItem ->
                    val product = allProducts.find { it.productId == cartItem.productId }
                    if (product == null) {
                        Log.w("OrderHistoryViewModel", "Продукт с id ${cartItem.productId} не найден в allProducts")
                    }
                    product?.let { it to cartItem.quantity }
                }

                if (basketItems.isEmpty()) {
                    Log.w("OrderHistoryViewModel", "Корзина пуста для пользователя с id: $currentUserId")
                    return@launch
                } else {
                    Log.d("OrderHistoryViewModel", "Найдено ${basketItems.size} элементов в корзине для пользователя с id: $currentUserId")
                }

// Вычисляем итоговую сумму заказа
                val totalSum = basketItems.sumOf { (product, quantity) ->
                    product.price.filter { it.isDigit() }.toInt() * quantity
                }
                Log.d("OrderHistoryViewModel", "Итоговая сумма заказа: $totalSum")

// Получаем текущее время оформления заказа
                val currentTime = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault()).format(Date())
                Log.d("OrderHistoryViewModel", "Время оформления заказа: $currentTime")

// Формируем объект заказа с сохранением списка productIds
                val order = Order(
                    userId = currentUserId,
                    orderTime = currentTime,
                    totalPrice = "$totalSum ₽",
                    productIds = basketItems.map { it.first.productId }
                )
                Log.d("OrderHistoryViewModel", "Создан заказ: $order")

// Сохраняем заказ в базу данных
                orderRepository.insertOrder(order)
                Log.d("OrderHistoryViewModel", "Заказ успешно сохранён в базе данных")

// Очищаем корзину для текущего пользователя
                cartItemRepository.clearCartByUserId(currentUserId)
                Log.d("OrderHistoryViewModel", "Корзина для пользователя с id: $currentUserId очищена")
            }
        } else {
            Log.w("OrderHistoryViewModel", "Пользователь не авторизован. Оформление заказа невозможно.")
        }
    }
}