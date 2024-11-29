package com.example.fishstore.db

import android.content.Context
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
        return StoreDatabase.getDatabase(appContext).storeDAO()
    }
}