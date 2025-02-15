package com.example.compstore.dao

import androidx.room.*
import com.example.compstore.modelDB.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartItem)

    @Delete
    suspend fun deleteCartItem(item: CartItem)

    @Query("SELECT * FROM cart_items WHERE userId = :userId")
    suspend fun getCartItemsByUserIdOnce(userId: Int): List<CartItem>

    @Query("DELETE FROM cart_items WHERE userId = :userId")
    suspend fun clearCartByUserId(userId: Int)

    @Query("SELECT * FROM cart_items WHERE userId = :userId")
    fun getCartItemsByUserIdFlow(userId: Int): Flow<List<CartItem>>

    // Новый метод для обновления количества товара
    @Query("UPDATE cart_items SET quantity = :quantity WHERE userId = :userId AND productId = :productId")
    suspend fun updateCartItemQuantity(userId: Int, productId: Int, quantity: Int)
}