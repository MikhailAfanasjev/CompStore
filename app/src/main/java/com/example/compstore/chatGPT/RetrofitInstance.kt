package com.example.compstore.chatGPT

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Retrofit builder для создания экземпляра ChatGPTApiService
object RetrofitInstance {
    private const val BASE_URL = "http://192.168.1.83:1234/"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10000, TimeUnit.SECONDS)
        .readTimeout(10000, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor())
        .build()

    val api: ChatGPTApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatGPTApiService::class.java)
    }
}