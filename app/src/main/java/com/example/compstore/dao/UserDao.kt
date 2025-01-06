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

    @Query("SELECT * FROM user LIMIT 1")
    fun getUserData(): Flow<User?>

    @Query("SELECT COUNT(*) FROM user")
    suspend fun getStoreCount(): Int

//    @Query("DELETE FROM user")
//    suspend fun clearUserData()

    @Query("SELECT * FROM user WHERE telephoneNumber = :phone LIMIT 1")
    suspend fun getUserByPhone(phone: String): User?

    @Update
    suspend fun updateUserData(user: User)
}
