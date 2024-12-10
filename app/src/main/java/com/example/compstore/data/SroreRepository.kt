package com.example.compstore.data

import android.util.Log
import com.example.compstore.modelDB.Store
import com.example.compstore.dao.StoreDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoreRepository @Inject constructor(private val storeDao: StoreDao) {
    suspend fun insertStore(store: Store) {
        Log.d("StoreRepository", "Inserting store: $store")
        storeDao.insertStore(store)
        Log.d("StoreRepository", "Store inserted successfully: $store")
    }
    suspend fun getStore(): Store? {
        return storeDao.getStore()
    }

    suspend fun hasStores(): Boolean {
        return storeDao.getStoreCount() > 0
    }
}