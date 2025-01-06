package com.example.compstore.repository

import android.util.Log
import com.example.compstore.dao.AddressDao
import com.example.compstore.modelDB.User
import com.example.compstore.dao.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

    fun getUserDataFlow(): Flow<User?> {
        Log.d("UserRepository", "getUserDataFlow called")
        return userDao.getUserData()
    }

    suspend fun insertUserData(user: User) {
        Log.d("UserRepository", "insertUserData called with user: $user")
        userDao.insertUserData(user)
    }

    suspend fun hasUsers(): Boolean {
        Log.d("UserRepository", "hasUsers called")
        val count = userDao.getStoreCount()
        val result = count > 0
        Log.d("UserRepository", "hasUsers result: $result (count: $count)")
        return result
    }

    suspend fun updateUserData(user: User) {
        Log.d("UserRepository", "updateUserData called with user: $user")
        userDao.updateUserData(user)
        Log.d("UserRepository", "User data updated")
    }

//    suspend fun clearUserData() {
//        Log.d("UserRepository", "clearUserData called")
//        userDao.clearUserData()
//        Log.d("UserRepository", "User data cleared")
//    }
}