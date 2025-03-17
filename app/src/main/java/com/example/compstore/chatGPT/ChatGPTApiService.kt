package com.example.compstore.chatGPT

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Streaming


interface ChatGPTApiService {
    @POST("v1/chat/completions")
    @Streaming
    suspend fun sendChatMessage(
        @Body request: ChatGPTRequest
    ): Response<ResponseBody>
}