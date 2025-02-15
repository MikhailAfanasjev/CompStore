package com.example.compstore.modelDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val name: String,
    val telephoneNumber: String,
    val email: String,
    val password: String,
    val isLoggedIn: Boolean = false,
    val paymentMethod: String
)