package com.example.compstore.repository

import android.util.Log
import com.example.compstore.dao.AddressDao
import com.example.compstore.modelDB.User
import com.example.compstore.dao.UserDao
import com.example.compstore.modelDB.Address
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoreRepository @Inject constructor(
    private val userDao: UserDao,
    private val addressDao: AddressDao
) {

    fun getUserDataFlow(): Flow<User?> {
        Log.d("StoreRepository", "getUserDataFlow called")
        return userDao.getUserData()
    }

    suspend fun insertUserData(user: User) {
        Log.d("StoreRepository", "insertUserData called with user: $user")
        userDao.insertUserData(user)
    }

    suspend fun hasUsers(): Boolean {
        Log.d("StoreRepository", "hasUsers called")
        val count = userDao.getStoreCount()
        val result = count > 0
        Log.d("StoreRepository", "hasUsers result: $result (count: $count)")
        return result
    }

    suspend fun updateUserData(user: User) {
        Log.d("StoreRepository", "updateUserData called with user: $user")
        userDao.updateUserData(user)
        Log.d("StoreRepository", "User data updated")
    }

    suspend fun clearUserData() {
        Log.d("StoreRepository", "clearUserData called")
        userDao.clearUserData()
        Log.d("StoreRepository", "User data cleared")
    }
}