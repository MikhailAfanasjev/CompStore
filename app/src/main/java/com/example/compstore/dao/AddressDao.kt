package com.example.compstore.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.compstore.modelDB.Address
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(address: Address)

    @Query("SELECT * FROM address")
    fun getAddresses(): Flow<List<Address>>

    @Update
    suspend fun updateAddress(address: Address)

    @Delete
    suspend fun deleteAddress(address: Address)
}