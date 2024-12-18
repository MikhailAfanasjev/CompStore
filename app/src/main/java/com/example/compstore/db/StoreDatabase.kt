package com.example.compstore.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.compstore.dao.UserDao
import com.example.compstore.modelDB.User

@Database(entities = [User::class], version = 1)
abstract class StoreDatabase : RoomDatabase() {
    abstract fun storeDAO(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: StoreDatabase? = null

        fun getDatabase(context: Context): StoreDatabase {
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