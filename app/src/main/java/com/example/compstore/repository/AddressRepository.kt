package com.example.compstore.repository
import android.util.Log
import com.example.compstore.modelDB.Address
import kotlinx.coroutines.flow.Flow
import com.example.compstore.dao.AddressDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressRepository @Inject constructor(
    private val addressDao: AddressDao)
{

    suspend fun insertAddress(address: Address) = addressDao.insertAddress(address)

    fun getUserAddressesFlow(): Flow<List<Address>> = addressDao.getAddresses()

    suspend fun updateAddress(address: Address) {
        Log.d("UserRepository", "updateAddress called with user: $address")
        addressDao.updateAddress(address)
        Log.d("UserRepository", "User data updated")
    }

    suspend fun deleteAddress(address: Address) {
        addressDao.deleteAddress(address)
    }
}