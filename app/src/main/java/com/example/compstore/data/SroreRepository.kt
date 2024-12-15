package com.example.compstore.data

import com.example.compstore.modelDB.User
import com.example.compstore.dao.UserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoreRepository @Inject constructor(private val userDao: UserDao) {

    fun getUserDataFlow(): Flow<User?> {
        return userDao.getUserData()
    }

    suspend fun insertUserData(user: User) {
        userDao.insertUserData(user)
    }

    suspend fun hasStores(): Boolean {
        return userDao.getStoreCount() > 0
    }
}