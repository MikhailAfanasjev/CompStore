package com.example.compstore.di

import android.content.Context
import androidx.work.WorkerParameters
import com.example.compstore.worker.StoreRememberWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideStoreRememberWorker(
        context: Context,
        workerParams: WorkerParameters
    ): StoreRememberWorker {
        return StoreRememberWorker(context, workerParams)
    }

    @Provides
    fun provideDeleteOnExit(): Boolean {
        return false // или загрузи это значение из настроек
    }
}