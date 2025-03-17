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

    val orders: StateFlow<List<Order>> = _userId
        .filterNotNull()
        .flatMapLatest { userId ->
            Log.d("OrderHistoryViewModel", "Получение заказов для пользователя с id: $userId")
            orderRepository.getOrdersByUserIdFlow(userId)
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

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

    fun completePurchase(allProducts: List<Product>, selectedProductIds: List<Int>? = null) {
        val currentUserId = _userId.value
        if (currentUserId != null) {
            viewModelScope.launch {
                // Собираем корзину
                val basketItems = cartItems.value.mapNotNull { cartItem ->
                    val product = allProducts.find { it.productId == cartItem.productId }
                    product?.let { it to cartItem.quantity }
                }

                // Фильтруем только выбранные товары, если они заданы
                val filteredBasketItems = if (!selectedProductIds.isNullOrEmpty()) {
                    basketItems.filter { (product, _) -> product.productId in selectedProductIds }
                } else {
                    basketItems
                }

                if (filteredBasketItems.isEmpty()) {
                    Log.w("OrderHistoryViewModel", "Нет выбранных товаров для оформления заказа")
                    return@launch
                }

                // Вычисляем итоговую сумму заказа
                val totalSum = filteredBasketItems.sumOf { (product, quantity) ->
                    product.price.filter { it.isDigit() }.toInt() * quantity
                }
                val currentTime = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault()).format(Date())

                val order = Order(
                    userId = currentUserId,
                    orderTime = currentTime,
                    totalPrice = "$totalSum ₽",
                    productIds = filteredBasketItems.map { it.first.productId }
                )
                Log.d("OrderHistoryViewModel", "Создан заказ: $order")

                orderRepository.insertOrder(order)
                Log.d("OrderHistoryViewModel", "Заказ успешно сохранён в базе данных")

                // Удаляем из корзины только купленные товары
                cartItemRepository.removeCartItems(currentUserId, selectedProductIds ?: emptyList())
                Log.d("OrderHistoryViewModel", "Удалены купленные товары из корзины пользователя с id: $currentUserId")
            }
        } else {
            Log.w("OrderHistoryViewModel", "Пользователь не авторизован. Оформление заказа невозможно.")
        }
    }
}