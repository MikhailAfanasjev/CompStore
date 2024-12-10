package com.example.compstore.dao

import android.content.Context
import android.util.Log
import com.example.compstore.db.StoreDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideStoreDao(@ApplicationContext appContext: Context): StoreDao {
        Log.d("DaoModule", "Providing StoreDao instance")
        return StoreDatabase.getDatabase(appContext).storeDAO()
    }
}