package com.example.compstore.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.compstore.modelDB.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(user: User)

    @Query("SELECT * FROM user")
    fun getUserData(): Flow<User?>

    @Query("SELECT COUNT(*) FROM user")
    suspend fun getStoreCount(): Int

//    @Query("DELETE FROM user")
//    suspend fun clearUserData()

    @Query("SELECT * FROM user WHERE telephoneNumber = :phone")
    suspend fun getUserByPhone(phone: String): User?

    @Update
    suspend fun updateUserData(user: User)

    @Query("UPDATE user SET isLoggedIn = 1 WHERE telephoneNumber = :phone")
    suspend fun setLoggedInUser(phone: String)

    @Query("UPDATE user SET isLoggedIn = 0 WHERE isLoggedIn = 1")
    suspend fun logoutAllUsers()

    @Query("SELECT * FROM user WHERE isLoggedIn = 1 LIMIT 1")
    fun getLoggedInUser(): Flow<User?>
}
