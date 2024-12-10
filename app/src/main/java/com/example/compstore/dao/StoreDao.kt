package com.example.compstore.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.compstore.modelDB.Store

@Dao
interface StoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStore(store: Store) {
        Log.d("StoreDao", "Inserting store: $store")
    }
    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getStore(): Store?

    @Query("SELECT COUNT(*) FROM user")
    suspend fun getStoreCount(): Int
}