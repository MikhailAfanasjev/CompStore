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

    val userAddress: StateFlow<Address?> = repository.getUserAddressFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        ).also {
            Log.d("StoreViewModelAddress", "User address state flow initialized")
        }

    fun saveUserAddress(city: String, street: String, house: String, apartment: String) {
        Log.d("StoreViewModelAddress", "Saving user address: city=$city, street=$street, house=$house, apartment=$apartment")
        viewModelScope.launch {
            try {
                val newAddress = Address(
                    city = city,
                    street = street,
                    house = house,
                    apartment = apartment
                )
                repository.saveAddress(newAddress)
                Log.d("StoreViewModelAddress", "User address saved successfully")
            } catch (e: Exception) {
                Log.e("StoreViewModelAddress", "Error saving user address: ${e.message}", e)
            }
        }
    }
}