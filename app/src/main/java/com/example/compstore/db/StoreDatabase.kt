package com.example.compstore.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.compstore.dao.StoreDao
import com.example.compstore.modelDB.Store

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