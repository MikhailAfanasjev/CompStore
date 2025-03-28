package com.example.compstore.modelDB

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "address",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("userId")]
)
data class Address(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val city: String,
    val street: String,
    val house: String,
    val apartment: String,
    val userId: Int // Связь с таблицей User
)