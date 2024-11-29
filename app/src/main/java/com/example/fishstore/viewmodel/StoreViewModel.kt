package com.example.fishstore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.fishstore.db.Store
import javax.inject.Inject
import com.example.fishstore.db.StoreDao


class StoreViewModel @Inject constructor(
    private val storeDao: StoreDao
) : ViewModel() {
    fun saveStore(store: Store) {
        viewModelScope.launch {
            storeDao.insertStore(store)
        }
    }
}