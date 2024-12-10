package com.example.compstore.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compstore.modelDB.Store
import com.example.compstore.data.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val repository: StoreRepository
) : ViewModel() {

    private val _store = MutableLiveData<Store?>()
    val store: LiveData<Store?> get() = _store

    fun saveStore(address: String, name: String, phone: String, email: String) {
        Log.d("StoreViewModel", "Preparing to save store details.")
        val store = Store(
            address = address,
            name = name,
            telephoneNumber = phone,
            email = email
        )
        Log.d("StoreViewModel", "Store object created: $store")
        viewModelScope.launch {
            try {
                repository.insertStore(store)
                Log.d("StoreViewModel", "Store saved successfully.")
            } catch (e: Exception) {
                Log.e("StoreViewModel", "Error saving store: ${e.message}", e)
            }
        }
    }

    fun checkIfStoresExist(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val hasStores = repository.hasStores()
            onResult(hasStores)
        }
    }

    fun getStore() {
        viewModelScope.launch {
            viewModelScope.launch {
                val fetchedStore = repository.getStore()
                _store.postValue(fetchedStore)
            }
        }
    }
}