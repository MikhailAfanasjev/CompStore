package com.example.compstore.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.compstore.modelDB.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)

    // Получение заказов для пользователя (однократно)
    @Query("SELECT * FROM orders WHERE userId = :userId")
    suspend fun getOrdersByUserIdOnce(userId: Int): List<Order>

    // Получение заказов для пользователя в виде Flow
    @Query("SELECT * FROM orders WHERE userId = :userId")
    fun getOrdersByUserIdFlow(userId: Int): Flow<List<Order>>

    // Удаление всех заказов для определённого пользователя
    @Query("DELETE FROM orders WHERE userId = :userId")
    suspend fun clearOrdersByUserId(userId: Int)
}