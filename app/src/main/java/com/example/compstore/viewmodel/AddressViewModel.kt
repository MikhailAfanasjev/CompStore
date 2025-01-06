package com.example.compstore.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compstore.modelDB.Address
import com.example.compstore.repository.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val repository: AddressRepository) : ViewModel() {
    val userAddresses: StateFlow<List<Address>> = repository.getUserAddressesFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun saveUserAddress(city: String, street: String, house: String, apartment: String) {
        viewModelScope.launch {
            try {
                val newAddress = Address(
                    city = city,
                    street = street,
                    house = house,
                    apartment = apartment
                )
                repository.insertAddress(newAddress)
            } catch (e: Exception) {
                Log.e("StoreViewModelAddress", "Error saving address: ${e.message}", e)
            }
        }
    }

    fun updateAddress(id: Int, city: String, street: String, house: String, apartment: String) {
        val updatedAddress = Address(
            id = id,
            city = city,
            street = street,
            house = house,
            apartment = apartment
        )
        Log.d("StoreViewModelAddress", "Updating user: $updatedAddress")
        viewModelScope.launch {
            try {
                repository.updateAddress(updatedAddress)
                Log.d("StoreViewModelAddress", "User data updated successfully")
            } catch (e: Exception) {
                Log.e("StoreViewModelAddress", "Error updating user: ${e.message}", e)
            }
        }
    }

    fun deleteAddress(address: Address) {
        viewModelScope.launch {
            repository.deleteAddress(address)
        }
    }
}