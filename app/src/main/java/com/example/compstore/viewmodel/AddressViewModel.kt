package com.example.compstore.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compstore.modelDB.Address
import com.example.compstore.repository.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val repository: AddressRepository
) : ViewModel() {

    // Приватное состояние для управления ID пользователя
    private val _userId = MutableStateFlow<Int?>(null)

    // Публичное состояние для чтения списка адресов пользователя
    val userAddresses: StateFlow<List<Address>> = _userId
        .filterNotNull()
        .flatMapLatest { userId ->
            Log.d("AddressViewModel", "Fetching addresses for userId: $userId")
            repository.getUserAddressesFlow(userId)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Приватное состояние для текущего адреса
    private val _currentAddress = MutableStateFlow<Address?>(null)
    val currentAddress: StateFlow<Address?> = _currentAddress

    // Установка текущего ID пользователя
    fun setUserId(userId: Int) {
        Log.d("AddressViewModel", "Setting userId to: $userId")
        _userId.value = userId
    }

    // Установка текущего редактируемого адреса
    fun setCurrentAddress(address: Address?) {
        Log.d("AddressViewModel", "Setting current address to: $address")
        _currentAddress.value = address
    }

    // Сохранение нового адреса
    fun saveUserAddress(
        city: String,
        street: String,
        house: String,
        apartment: String,
        currentUserId: Int
    ) {
        viewModelScope.launch {
            _userId.value?.let { userId ->
                val newAddress = Address(
                    city = city,
                    street = street,
                    house = house,
                    apartment = apartment,
                    userId = userId
                )
                try {
                    Log.d("AddressViewModel", "Saving new address: $newAddress")
                    repository.insertAddress(newAddress)
                } catch (e: Exception) {
                    Log.e("AddressViewModel", "Error saving address: ${e.message}", e)
                }
            } ?: Log.w("AddressViewModel", "Cannot save address: userId is null")
        }
    }

    // Обновление существующего адреса
    fun updateAddress(
        id: Int,
        city: String,
        street: String,
        house: String,
        apartment: String,
        currentUserId: Int
    ) {
        viewModelScope.launch {
            _userId.value?.let { userId ->
                val updatedAddress = Address(
                    id = id,
                    city = city,
                    street = street,
                    house = house,
                    apartment = apartment,
                    userId = userId
                )
                try {
                    Log.d("AddressViewModel", "Updating address: $updatedAddress")
                    repository.updateAddress(updatedAddress)
                } catch (e: Exception) {
                    Log.e("AddressViewModel", "Error updating address: ${e.message}", e)
                }
            } ?: Log.w("AddressViewModel", "Cannot update address: userId is null")
        }
    }

    // Удаление адреса
    fun deleteAddress(address: Address) {
        viewModelScope.launch {
            try {
                Log.d("AddressViewModel", "Deleting address: $address")
                repository.deleteAddress(address)
            } catch (e: Exception) {
                Log.e("AddressViewModel", "Error deleting address: ${e.message}", e)
            }
        }
    }
}