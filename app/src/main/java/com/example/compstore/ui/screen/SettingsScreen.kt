package com.example.compstore.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.compstore.utils.scaleDimension
import com.example.linguareader.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Настройки") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(scaleDimension(16.dp))
        ) {
            Text(
                text = "Информация о приложении",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(scaleDimension(8.dp)))

            // Основная информация о приложении
            Text(text = "Название: CompStore")
            Text(text = "Версия: 1.0.0")
            Text(text = "Разработчик: Misha")

            Spacer(modifier = Modifier.height(scaleDimension(16.dp)))
            Text(
                text = "Описание: Это демонстрационное приложение, " +
                        "показывающее работу с Jetpack Compose.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}