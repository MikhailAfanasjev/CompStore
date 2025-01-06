package com.example.compstore.modelDB

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class Address(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val city: String,
    val street: String,
    val house: String,
    val apartment: String
)