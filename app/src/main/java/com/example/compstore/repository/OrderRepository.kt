package com.example.compstore.repository

import android.util.Log
import com.example.compstore.dao.OrderDao
import com.example.compstore.modelDB.Order
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val orderDao: OrderDao
) {

    // Метод для добавления/сохранения заказа
    suspend fun insertOrder(order: Order) {
        Log.d("OrderRepository", "Inserting order: $order")
        orderDao.insertOrder(order)
        Log.d("OrderRepository", "Order inserted successfully.")
    }

    // Метод для удаления заказа
    suspend fun deleteOrder(order: Order) {
        Log.d("OrderRepository", "Deleting order: $order")
        orderDao.deleteOrder(order)
        Log.d("OrderRepository", "Order deleted successfully.")
    }

    // Метод для очистки всех заказов пользователя
    suspend fun clearOrdersByUserId(userId: Int) {
        Log.d("OrderRepository", "Clearing orders for userId: $userId")
        orderDao.clearOrdersByUserId(userId)
        Log.d("OrderRepository", "Orders cleared successfully for userId: $userId")
    }

    // Получение заказов пользователя (однократно)
    suspend fun getOrdersByUserIdOnce(userId: Int): List<Order> {
        return orderDao.getOrdersByUserIdOnce(userId)
    }

    // Получение заказов пользователя в виде Flow
    fun getOrdersByUserIdFlow(userId: Int): Flow<List<Order>> {
        return orderDao.getOrdersByUserIdFlow(userId)
    }
}