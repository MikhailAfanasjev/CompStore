package com.example.compstore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compstore.chatGPT.ChatGPTRequest
import com.example.compstore.chatGPT.Message
import com.example.compstore.chatGPT.RetrofitInstance
import com.example.compstore.utils.cleanResponse
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {
    private val _chatMessages = MutableStateFlow<List<Message>>(emptyList())
    val chatMessages: StateFlow<List<Message>> = _chatMessages.asStateFlow()

    fun addMessage(message: Message) {
        _chatMessages.value += message
    }

    fun updateLastAssistantMessage(updatedContent: String) {
        val messages = _chatMessages.value.toMutableList()
        val index = messages.indexOfLast { it.role == "assistant" }
        if (index != -1) {
            messages[index] = messages[index].copy(content = updatedContent)
            _chatMessages.value = messages
        }
    }

    // функция отправки сообщения, выполняющая сетевой запрос
    fun sendMessage(inputText: String) {
        // Сохраняем, пуст ли диалог до добавления нового сообщения
        val isFirstRequest = _chatMessages.value.isEmpty()

        // Добавляем новое сообщение пользователя в историю
        addMessage(Message(role = "user", content = inputText))

        viewModelScope.launch(Dispatchers.IO) {
            val allMessages = mutableListOf<Message>()
            // Если это первый запрос, добавляем системное сообщение
            if (isFirstRequest) {
                allMessages.add(
                    Message(
                        role = "system",
                        content = "Ты консультант в компьютерном магазине. Помогай клиентам с выбором компьютеров, комплектующих и периферии. Пиши только на русском"
                    )
                )
            }
            // Добавляем всю историю диалога (пользовательские и ответы ассистента)
            allMessages.addAll(_chatMessages.value)

            val request = ChatGPTRequest(
                model = "deepseek-r1-distill-llama-7b",
                messages = allMessages,
                stream = true
            )

            try {
                val response = RetrofitInstance.api.sendChatMessage(request)
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        val source = body.source()
                        val gson = Gson()
                        val answerBuilder = StringBuilder()
                        var assistantMessageAdded = false

                        while (!source.exhausted()) {
                            val line = source.readUtf8Line() ?: break
                            if (line.trim() == "data: [DONE]") break

                            if (line.startsWith("data:")) {
                                val jsonLine = line.removePrefix("data:").trim()
                                try {
                                    val jsonObject = gson.fromJson(jsonLine, JsonObject::class.java)
                                    if (jsonObject.has("choices")) {
                                        val choicesArray = jsonObject.getAsJsonArray("choices")
                                        if (choicesArray.size() > 0) {
                                            val deltaObject = choicesArray[0]
                                                .asJsonObject.getAsJsonObject("delta")
                                            val content = deltaObject
                                                ?.get("content")
                                                ?.asString ?: ""
                                            if (content.isNotEmpty()) {
                                                answerBuilder.append(content)
                                                val fullAnswer = answerBuilder.toString()

                                                if (!assistantMessageAdded && fullAnswer.contains("</think>")) {
                                                    val contentAfterThink = fullAnswer.substringAfter("</think>").trim()
                                                    withContext(Dispatchers.Main) {
                                                        addMessage(
                                                            Message(
                                                                role = "assistant",
                                                                content = cleanResponse(contentAfterThink)
                                                            )
                                                        )
                                                    }
                                                    assistantMessageAdded = true
                                                } else if (assistantMessageAdded) {
                                                    val updatedContent = fullAnswer.substringAfter("</think>").trim()
                                                    withContext(Dispatchers.Main) {
                                                        updateLastAssistantMessage(
                                                            cleanResponse(updatedContent)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        addMessage(
                            Message(
                                role = "assistant",
                                content = "Ошибка: ${response.code()} - ${response.message()}"
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    addMessage(
                        Message(
                            role = "assistant",
                            content = "Exception: ${e.localizedMessage}"
                        )
                    )
                }
            }
        }
    }
}