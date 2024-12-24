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
    fun provideUserDao(@ApplicationContext appContext: Context): UserDao {
        Log.d("DaoModule", "Providing UserDao instance")
        return StoreDatabase.getDatabase(appContext).userDAO()
    }

    @Provides
    @Singleton
    fun provideAddressDao(@ApplicationContext appContext: Context): AddressDao {
        Log.d("DaoModule", "Providing UserDao instance")
        return StoreDatabase.getDatabase(appContext).addressDAO()
    }
}