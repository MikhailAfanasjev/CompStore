package com.example.compstore.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.compstore.db.Store
import com.example.compstore.db.StoreDao
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

class SroreRepository @Inject constructor(private val storeDao: StoreDao) {

}