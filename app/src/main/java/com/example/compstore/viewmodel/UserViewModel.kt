package com.example.compstore.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compstore.modelDB.User
import com.example.compstore.repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: StoreRepository) : ViewModel() {

    val user: StateFlow<User?> = repository.getUserDataFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        ).also {
            Log.d("StoreViewModelAddress", "User state flow initialized")
        }

    fun saveUser(name: String, phone: String, email: String, password: String) {
        val updatedUser = User(
            name = name,
            telephoneNumber = phone,
            email = email,
            password = password
        )
        Log.d("StoreViewModelAddress", "Saving user: $updatedUser")
        viewModelScope.launch {
            try {
                repository.insertUserData(updatedUser)
                Log.d("StoreViewModelAddress", "User data saved successfully")
                repository.getUserDataFlow().collectLatest {
                    Log.d("StoreViewModelAddress", "Emitting updated user data: $it")
                    (user as MutableStateFlow).emit(it)
                }
            } catch (e: Exception) {
                Log.e("StoreViewModelAddress", "Error saving user: ${e.message}", e)
            }
        }
    }

    fun updateUser(id: Int, name: String, phone: String, email: String, password: String) {
        val updatedUser = User(
            id = id,
            name = name,
            telephoneNumber = phone,
            email = email,
            password = password
        )
        Log.d("StoreViewModelAddress", "Updating user: $updatedUser")
        viewModelScope.launch {
            try {
                repository.updateUserData(updatedUser)
                Log.d("StoreViewModelAddress", "User data updated successfully")
            } catch (e: Exception) {
                Log.e("StoreViewModelAddress", "Error updating user: ${e.message}", e)
            }
        }
    }

    fun logoutUser() {
        Log.d("StoreViewModelAddress", "Logging out user")
        viewModelScope.launch {
            try {
                repository.clearUserData()
                Log.d("StoreViewModelAddress", "User data cleared successfully")
            } catch (e: Exception) {
                Log.e("StoreViewModelAddress", "Error clearing user data: ${e.message}", e)
            }
        }
    }

    suspend fun hasUserData(): Boolean {
        Log.d("StoreViewModelAddress", "Checking if user data exists")
        return try {
            val result = repository.hasUsers()
            Log.d("StoreViewModelAddress", "Has user data: $result")
            result
        } catch (e: Exception) {
            Log.e("StoreViewModelAddress", "Error checking user data: ${e.message}", e)
            false
        }
    }
}