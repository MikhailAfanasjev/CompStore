package com.example.compstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import com.example.compstore.chatGPT.ApiKeyHelper
import com.example.compstore.nav.NavGraph
import com.example.compstore.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализируем зашифрованное хранилище, где хранится API‑ключ локально
        ApiKeyHelper.init(this)

        setContent {
            MaterialTheme {
                NavGraph()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        userViewModel.clearUserIfNotRemembered()
    }
}