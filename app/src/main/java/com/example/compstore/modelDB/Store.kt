package com.example.compstore.modelDB

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class Store(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val address: String,
    val name: String,
    val telephoneNumber: String,
    val email: String
) {
    init {
        Log.d("Store", "Store entity created: $this")
    }
}