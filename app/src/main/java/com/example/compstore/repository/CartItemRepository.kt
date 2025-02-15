package com.example.compstore.repository

import android.util.Log
import com.example.compstore.dao.CartItemDao
import com.example.compstore.modelDB.CartItem
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.GPU
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartItemRepository @Inject constructor(
    private val cartItemDao: CartItemDao
) {

    companion object {
        private const val TAG = "CartItemRepository"
    }

    suspend fun getCartItemsByUserIdOnce(userId: Int): List<CartItem> {
        return cartItemDao.getCartItemsByUserIdOnce(userId)
    }

    suspend fun addCartItem(item: CartItem) {
        Log.d("CartItemRepository", "Adding/Updating cart item: $item")
        cartItemDao.insertCartItem(item)
        Log.d("CartItemRepository", "Cart item inserted/updated successfully.")
    }

    suspend fun removeCartItem(item: CartItem) {
        Log.d("CartItemRepository", "Removing cart item: $item")
        cartItemDao.deleteCartItem(item)
        Log.d("CartItemRepository", "Cart item removed successfully.")
    }

    suspend fun clearCartByUserId(userId: Int) {
        Log.d(TAG, "Clearing cart for userId: $userId")
        cartItemDao.clearCartByUserId(userId)
        Log.d(TAG, "Cart cleared for userId: $userId")
    }

    fun getCartItemsByUserIdFlow(userId: Int): Flow<List<CartItem>> {
        return cartItemDao.getCartItemsByUserIdFlow(userId)
    }

    // Новый метод для обновления количества товара
    suspend fun updateCartItemQuantity(userId: Int, productId: Int, quantity: Int) {
        Log.d(TAG, "Updating quantity for userId: $userId, productId: $productId to $quantity")
        cartItemDao.updateCartItemQuantity(userId, productId, quantity)
        Log.d(TAG, "Quantity updated successfully.")
    }
}