package com.example.compstore.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.compstore.ui.components.ChatMessageItem
import com.example.compstore.ui.components.CustomButton
import com.example.compstore.ui.theme.AccentOrange
import com.example.compstore.ui.theme.BackgroundLightGrey
import com.example.compstore.ui.theme.PrimaryText
import com.example.compstore.utils.scaleDimension
import com.example.compstore.viewmodel.ChatViewModel
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(chatViewModel: ChatViewModel) {
    var userInput by remember { mutableStateOf("") }
    val chatMessages by chatViewModel.chatMessages.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(chatMessages) {
        coroutineScope.launch {
            listState.animateScrollToItem(chatMessages.size)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundLightGrey
    ) {
        Column(
            modifier = Modifier
                .padding(scaleDimension(16.dp))
                .fillMaxSize()
        ) {
            Text(
                text = "Чат с консультантом",
                style = MaterialTheme.typography.headlineMedium,
                color = PrimaryText
            )
            Spacer(modifier = Modifier.height(scaleDimension(16.dp)))

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(chatMessages) { message ->
                    ChatMessageItem(message = message)
                }
            }

            OutlinedTextField(
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text("Сообщение консультанту", color = PrimaryText) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = PrimaryText),
                maxLines = 10
            )
            Spacer(modifier = Modifier.height(scaleDimension(8.dp)))
            // Заменяем стандартную кнопку "Send" на CustomButton
            CustomButton(
                text = "Send",
                onClick = {
                    if (userInput.isNotBlank()) {
                        chatViewModel.sendMessage(userInput)
                        userInput = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}