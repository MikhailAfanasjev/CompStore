package com.example.compstore.modelDB

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

const val LOCAL_USER_ID = -1

@Entity(
    tableName = "cart_items",
    primaryKeys = ["userId", "productId"],
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("userId")]
)
data class CartItem(
    val userId: Int,
    val productId: Int,
    val quantity: Int = 1
)