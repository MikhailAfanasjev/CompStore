package com.example.compstore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compstore.data.StoreRepository
import com.example.compstore.modelDB.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StoreViewModel @Inject constructor(private val repository: StoreRepository) : ViewModel() {

    val user: StateFlow<User?> = repository.getUserDataFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun saveUser(name: String, phone: String, email: String, password: String) {
        val updatedUser = User(
            name = name,
            telephoneNumber = phone,
            email = email,
            password = password
        )
        viewModelScope.launch {
            repository.insertUserData(updatedUser)
        }
    }
    suspend fun hasUserData(): Boolean {
        return repository.hasStores()
    }
    fun logoutUser() {
        viewModelScope.launch {
            repository.clearUserData()
        }
    }
    fun saveUserAddress(city: String, street: String, house: String, apartment: String) {
        viewModelScope.launch {
            // Пример добавления адреса в базу данных (будем использовать соответствующую сущность в репозитории)
            val newAddress = Address(city = city, street = street, house = house, apartment = apartment)
            repository.saveAddress(newAddress)
        }
    }
}