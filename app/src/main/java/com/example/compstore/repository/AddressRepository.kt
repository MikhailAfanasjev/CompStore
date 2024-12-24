package com.example.compstore.repository

import android.util.Log
import com.example.compstore.modelDB.Address
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.compstore.dao.AddressDao

class AddressRepository {
    suspend fun saveAddress(address: Address) {
        Log.d("StoreRepository", "saveAddress called with address: $address")
        addressDao.insertAddress(address)
        Log.d("StoreRepository", "Address saved")
    }

    fun getUserAddressFlow(): Flow<Address?> {
        Log.d("StoreRepository", "getUserAddressFlow called")
        return addressDao.getAddresses()
            .map { addresses ->
                val address = addresses.firstOrNull()
                Log.d("StoreRepository", "First address in flow: $address")
                address
            }
    }
}