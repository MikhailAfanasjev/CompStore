package com.example.compstore.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    @Query("DELETE FROM user")
    suspend fun clearUserData()
}