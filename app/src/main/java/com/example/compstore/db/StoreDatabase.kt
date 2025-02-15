package com.example.compstore.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.compstore.dao.AddressDao
import com.example.compstore.dao.CartItemDao
import com.example.compstore.dao.OrderDao
import com.example.compstore.dao.UserDao
import com.example.compstore.modelDB.User
import com.example.compstore.modelDB.Address
import com.example.compstore.modelDB.CartItem
import com.example.compstore.modelDB.Order

@Database(entities = [User::class, Address::class, CartItem::class, Order::class], version = 1)
abstract class StoreDatabase : RoomDatabase() {

    abstract fun userDAO(): UserDao
    abstract fun addressDAO(): AddressDao
    abstract fun cartItemDAO(): CartItemDao
    abstract fun orderDAO(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: StoreDatabase? = null

        fun getDatabase(context: Context): StoreDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StoreDatabase::class.java,
                    "storeNote"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}