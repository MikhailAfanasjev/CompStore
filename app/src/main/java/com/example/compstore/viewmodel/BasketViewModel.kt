package com.example.compstore.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compstore.modelDB.CartItem
import com.example.compstore.modelDB.LOCAL_USER_ID
import com.example.compstore.modelDB.Order
import com.example.compstore.repository.CartItemRepository
import com.example.compstore.repository.UserRepository
import com.example.compstore.ui.screen.PCComponentsScreens.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val cartItemRepository: CartItemRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userId = MutableStateFlow<Int?>(null)
    val userId: StateFlow<Int?> get() = _userId

    // Локальное хранилище корзины для неавторизованных пользователей.
    private val _localCartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val localCartItems: StateFlow<List<CartItem>> = _localCartItems

    val cartItems: StateFlow<List<CartItem>> = _userId
        .flatMapLatest { userId ->
            if (userId != null && userId != LOCAL_USER_ID) {
                cartItemRepository.getCartItemsByUserIdFlow(userId)
            } else {
                _localCartItems
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            userRepository.getLoggedInUser().collectLatest { user ->
                if (user != null) {
                    Log.d("BasketViewModel", "Logged-in user found with ID: ${user.id}")
                    setUserId(user.id)  // сохраняем реальный ID пользователя
                    // Здесь можно добавить логику слияния локальной корзины с корзиной из БД
                } else {
                    Log.d("BasketViewModel", "No logged-in user found. Using local cart.")
                    setUserId(null)  // или, если хотите, можно явно задать LOCAL_USER_ID
                }
            }
        }
    }

    private fun setUserId(userId: Int?) {
        Log.d("BasketViewModel", "Setting userId to: $userId")
        _userId.value = userId
    }

    fun addToCart(productId: Int) {
        val currentUserId = _userId.value
        if (currentUserId != null && currentUserId != LOCAL_USER_ID) {
            // Пользователь авторизован — сохраняем в БД.
            viewModelScope.launch {
                val currentCartItems = cartItemRepository.getCartItemsByUserIdOnce(currentUserId)
                val existingItem = currentCartItems.find { it.productId == productId }
                if (existingItem != null) {
                    val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
                    cartItemRepository.addCartItem(updatedItem)
                    Log.d("BasketViewModel", "Updated quantity for productId $productId: ${updatedItem.quantity}")
                } else {
                    cartItemRepository.addCartItem(
                        CartItem(userId = currentUserId, productId = productId, quantity = 1)
                    )
                    Log.d("BasketViewModel", "Added new productId $productId to cart")
                }
            }
        } else {
            // Пользователь не авторизован — сохраняем товары локально.
            val currentList = _localCartItems.value.toMutableList()
            val existingItem = currentList.find { it.productId == productId }
            if (existingItem != null) {
                val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
                val index = currentList.indexOf(existingItem)
                currentList[index] = updatedItem
                Log.d("BasketViewModel", "Locally updated quantity for productId $productId: ${updatedItem.quantity}")
            } else {
                currentList.add(CartItem(userId = LOCAL_USER_ID, productId = productId, quantity = 1))
                Log.d("BasketViewModel", "Locally added new productId $productId to cart")
            }
            _localCartItems.value = currentList
            Log.d("BasketViewModel", "Local cart updated: ${_localCartItems.value.size} items")
        }
    }

    fun removeFromCart(productId: Int) {
        val currentUserId = _userId.value
        if (currentUserId != null && currentUserId != LOCAL_USER_ID) {
            viewModelScope.launch {
                val currentCartItems = cartItemRepository.getCartItemsByUserIdOnce(currentUserId)
                val existingItem = currentCartItems.find { it.productId == productId }
                if (existingItem != null) {
                    if (existingItem.quantity > 1) {
                        val updatedItem = existingItem.copy(quantity = existingItem.quantity - 1)
                        cartItemRepository.addCartItem(updatedItem)
                        Log.d("BasketViewModel", "Decreased quantity for productId $productId: ${updatedItem.quantity}")
                    } else {
                        cartItemRepository.removeCartItem(existingItem)
                        Log.d("BasketViewModel", "Removed productId $productId from cart")
                    }
                } else {
                    Log.w("BasketViewModel", "Cart item not found for removal")
                }
            }
        } else {
            val currentList = _localCartItems.value.toMutableList()
            val existingItem = currentList.find { it.productId == productId }
            if (existingItem != null) {
                if (existingItem.quantity > 1) {
                    val updatedItem = existingItem.copy(quantity = existingItem.quantity - 1)
                    val index = currentList.indexOf(existingItem)
                    currentList[index] = updatedItem
                    Log.d("BasketViewModel", "Locally decreased quantity for productId $productId: ${updatedItem.quantity}")
                } else {
                    currentList.remove(existingItem)
                    Log.d("BasketViewModel", "Locally removed productId $productId from cart")
                }
                _localCartItems.value = currentList
                Log.d("BasketViewModel", "Local cart updated after removal: ${_localCartItems.value.size} items")
            } else {
                Log.w("BasketViewModel", "Local cart item not found for removal")
            }
        }
    }

    fun removeProductFromCart(productId: Int) {
        val currentUserId = _userId.value
        if (currentUserId != null && currentUserId != LOCAL_USER_ID) {
            viewModelScope.launch {
                val currentCartItems = cartItemRepository.getCartItemsByUserIdOnce(currentUserId)
                val existingItem = currentCartItems.find { it.productId == productId }
                if (existingItem != null) {
                    cartItemRepository.removeCartItem(existingItem)
                    Log.d("BasketViewModel", "Completely removed productId $productId from cart")
                }
            }
        } else {
            val currentList = _localCartItems.value.toMutableList()
            val removed = currentList.removeAll { it.productId == productId }
            if (removed) {
                Log.d("BasketViewModel", "Locally completely removed productId $productId from cart")
                _localCartItems.value = currentList
            } else {
                Log.w("BasketViewModel", "Local cart item not found for complete removal")
            }
        }
    }

    fun clearCart() {
        val currentUserId = _userId.value
        if (currentUserId != null) {
            viewModelScope.launch {
                Log.d("BasketViewModel", "Clearing cart for userId: $currentUserId")
                cartItemRepository.clearCartByUserId(currentUserId)
            }
        } else {
            Log.d("BasketViewModel", "Clearing local cart for non-logged in user")
            _localCartItems.value = emptyList()
        }
    }
}