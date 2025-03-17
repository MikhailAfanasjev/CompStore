package com.example.compstore.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compstore.modelDB.User
import com.example.compstore.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val _paymentMethod = MutableStateFlow("Не выбран")
    val paymentMethod: StateFlow<String> get() = _paymentMethod

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user


    val loggedInUser: StateFlow<User?> = repository.getLoggedInUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    init {
        viewModelScope.launch {
            repository.getLoggedInUser().collectLatest { currentUser ->
                _user.value = currentUser
                Log.d("UserViewModel", "User loaded on startup: $currentUser")
            }
        }
    }

    fun loginUser(phone: String, rememberMe: Boolean) {
        viewModelScope.launch {
            try {
                repository.setLoggedInUser(phone)
                val userFromDb = repository.getUserByPhone(phone)
                _user.value = userFromDb
                Log.d("UserViewModel", "User logged in: $phone, rememberMe: $rememberMe")

                if (!rememberMe) {
                    repository.markUserForDeletion() // Помечаем пользователя для удаления при выходе
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error logging in user: ${e.message}", e)
            }
        }
    }

    fun logoutUser() {
        viewModelScope.launch {
            try {
                repository.logoutUser()
                _user.value = null
                Log.d("UserViewModel", "User logged out successfully")
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error logging out user: ${e.message}", e)
            }
        }
    }

    fun clearUserIfNotRemembered() {
        viewModelScope.launch {
            try {
                val shouldDelete = repository.shouldDeleteUserOnExit()
                if (shouldDelete) {
                    repository.clearUserData()
                    Log.d("UserViewModel", "User data cleared on app exit")
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error clearing user data on exit: ${e.message}", e)
            }
        }
    }

    fun saveUser(name: String, phone: String, email: String, password: String) {
        val updatedUser = User(
            name = name,
            telephoneNumber = phone,
            email = email,
            password = password,
            isLoggedIn = true,
            paymentMethod = paymentMethod.toString()
        )
        Log.d("UserViewModelAddress", "Saving user: $updatedUser")
        viewModelScope.launch {
            try {
                repository.insertUserData(updatedUser)
                Log.d("UserViewModelAddress", "User data saved successfully")
                repository.getUserDataFlow().collectLatest {
                    Log.d("UserViewModelAddress", "Emitting updated user data: $it")
                    (user as MutableStateFlow).emit(it)
                }
            } catch (e: Exception) {
                Log.e("UserViewModelAddress", "Error saving user: ${e.message}", e)
            }
        }
    }

    fun updateUser(id: Int, name: String, phone: String, email: String, password: String) {
        val updatedUser = User(
            id = id,
            name = name,
            telephoneNumber = phone,
            email = email,
            password = password,
            isLoggedIn = true,
            paymentMethod = paymentMethod.toString()
        )
        Log.d("UserViewModelAddress", "Updating user: $updatedUser")
        viewModelScope.launch {
            try {
                repository.updateUserData(updatedUser)
                Log.d("UserViewModelAddress", "User data updated successfully")
            } catch (e: Exception) {
                Log.e("UserViewModelAddress", "Error updating user: ${e.message}", e)
            }
        }
    }

    suspend fun hasUserData(): Boolean {
        Log.d("UserViewModelAddress", "Checking if user data exists")
        return try {
            val result = repository.hasUsers()
            Log.d("UserViewModelAddress", "Has user data: $result")
            result
        } catch (e: Exception) {
            Log.e("UserViewModelAddress", "Error checking user data: ${e.message}", e)
            false
        }
    }

    suspend fun authenticateUser(login: String, password: String): Boolean {
        Log.d("UserViewModel", "Authenticating user with login: $login")
        return try {
            val user = repository.getUserByPhone(login)
            if (user != null && user.password == password) {
                Log.d("UserViewModel", "Authentication successful for user: ${user.name}")
                true
            } else {
                Log.d("UserViewModel", "Authentication failed: incorrect login or password")
                false
            }
        } catch (e: Exception) {
            Log.e("UserViewModel", "Error during authentication: ${e.message}", e)
            false
        }
    }

    fun updatePaymentMethod(method: String) {
        viewModelScope.launch {
            loggedInUser.value?.let { currentUser ->
                val updatedUser = currentUser.copy(paymentMethod = method)
                try {
                    repository.updateUserData(updatedUser)
                    _paymentMethod.value = method
                    Log.d("UserViewModel", "Payment method updated and saved: $method")
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Error updating payment method: ${e.message}", e)
                }
            }
        }
    }
}