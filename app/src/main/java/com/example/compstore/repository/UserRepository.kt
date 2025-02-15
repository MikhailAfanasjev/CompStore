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

    suspend fun getUserByPhone(phone: String): User? {
        Log.d("UserRepository", "getUserByPhone called with phone: $phone")
        return userDao.getUserByPhone(phone)
    }

    suspend fun updateUserData(user: User) {
        Log.d("UserRepository", "updateUserData called with user: $user")
        userDao.updateUserData(user)
        Log.d("UserRepository", "User data updated")
    }

    fun getLoggedInUser(): Flow<User?> {
        Log.d("UserRepository", "getLoggedInUser called")
        return userDao.getLoggedInUser()
    }

    suspend fun setLoggedInUser(phone: String) {
        Log.d("UserRepository", "setLoggedInUser called for phone: $phone")
        userDao.logoutAllUsers() // Сбрасываем статус всех пользователей
        userDao.setLoggedInUser(phone) // Устанавливаем флаг для нового пользователя
    }

    suspend fun logoutUser() {
        Log.d("UserRepository", "logoutUser called")
        userDao.logoutAllUsers() // Сбрасываем всех пользователей
    }

//    suspend fun clearUserData() {
//        Log.d("UserRepository", "clearUserData called")
//        userDao.clearUserData()
//        Log.d("UserRepository", "User data cleared")
//    }
}