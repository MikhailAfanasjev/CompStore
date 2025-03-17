package com.example.compstore.chatGPT

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = ApiKeyHelper.getApiKey()
        val newRequest = chain.request().newBuilder()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer $apiKey")
            .build()
        return chain.proceed(newRequest)
    }
}


// Интерсептор добавляет зашифрованный API‑ключ в заголовок каждого запроса
//class AuthInterceptor : Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val encryptedKey = ApiKeyHelper.encryptApiKey()
//        val newRequest = chain.request().newBuilder()
//            .header("Content-Type", "application/json")
//            .header("Authorization", "Bearer $encryptedKey")
//            .build()
//        return chain.proceed(newRequest)
//    }
//}