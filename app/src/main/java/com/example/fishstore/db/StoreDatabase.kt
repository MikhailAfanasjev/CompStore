package com.example.fishstore.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Store::class], version = 1)
abstract class StoreDatabase : RoomDatabase() {
    abstract fun storeDAO(): StoreDao

    companion object {
        @Volatile
        private var INSTANCE: StoreDatabase? = null

        fun getDatabase(context: Context): StoreDatabase { // Получаем экземпляр базы данных
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StoreDatabase::class.java,
                    "storeNote"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}