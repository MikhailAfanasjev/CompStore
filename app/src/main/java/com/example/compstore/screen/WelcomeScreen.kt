package com.example.compstore.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.linguareader.R

@Composable
fun WelcomeScreen(onNavigateToAddressSelection: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // Используем Box для наложения текста и кнопки поверх изображения
        Box(modifier = Modifier.fillMaxSize()) {
            // Фоновое изображение
            AsyncImage(
                model = R.drawable.fone2, // Укажите ваш локальный ресурс
                contentDescription = "Фоновое изображение",
                contentScale = ContentScale.Crop, // Обрезка изображения под размер контейнера
                modifier = Modifier.fillMaxSize()
            )
            // Остальной контент
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Добро пожаловать",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onNavigateToAddressSelection) {
                    Text("Далее")
                }
            }
        }
    }
}