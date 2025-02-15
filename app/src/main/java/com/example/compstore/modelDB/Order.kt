package com.example.compstore.modelDB

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "orders",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("userId")]
)
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,      // Автогенерируемый идентификатор заказа
    val userId: Int,           // Идентификатор пользователя, сделавшего заказ
    val orderTime: String,     // Время заказа (например, "12:30 14.02.2025")
    val totalPrice: String     // Итоговая сумма заказа (например, "1500 ₽")
)