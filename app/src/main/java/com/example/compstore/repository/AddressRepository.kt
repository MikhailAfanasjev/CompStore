package com.example.compstore.repository
import android.util.Log
import com.example.compstore.modelDB.Address
import kotlinx.coroutines.flow.Flow
import com.example.compstore.dao.AddressDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressRepository @Inject constructor(
    private val addressDao: AddressDao
) {

    suspend fun insertAddress(address: Address) = addressDao.insertAddress(address)

    fun getUserAddressesFlow(userId: Int): Flow<List<Address>> =
        addressDao.getAddressesForUser(userId)

    suspend fun updateAddress(address: Address) {
        addressDao.updateAddress(address)
    }

    suspend fun deleteAddress(address: Address) {
        addressDao.deleteAddress(address)
    }
}