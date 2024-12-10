package com.example.compstore.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.compstore.viewmodel.StoreViewModel
import com.example.linguareader.R

@Composable
fun AddressSelectionScreen(
    onSave: () -> Unit,
    onSkip: () -> Unit,
    storeViewModel: StoreViewModel = hiltViewModel()
) {
    Log.d("AddressSelectionScreen", "Rendering AddressSelectionScreen")

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = R.drawable.fone,
                contentDescription = "Фоновое изображение",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Address Selection Screen",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Please fill in your details below or skip to continue.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                var address by remember { mutableStateOf("") }
                var name by remember { mutableStateOf("") }
                var telephoneNumber by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                var showError by remember { mutableStateOf(false) }

                OutlinedTextField(
                    value = address,
                    onValueChange = {
                        address = it
                        showError = false
                        Log.d("AddressSelectionScreen", "Address input changed: $address")
                    },
                    label = { Text("Адрес доставки") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && address.isEmpty()
                )
                if (showError && address.isEmpty()) {
                    Text(
                        text = "Адрес обязателен",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        if (it.all { char -> char.isLetter() || char.isWhitespace() }) {
                            name = it
                            showError = false
                            Log.d("AddressSelectionScreen", "Name input changed: $name")
                        }
                    },
                    label = { Text("Имя") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && name.isEmpty()
                )
                if (showError && name.isEmpty()) {
                    Text(
                        text = "Имя обязательно",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = telephoneNumber,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) {
                            telephoneNumber = it
                            showError = false
                            Log.d("AddressSelectionScreen", "Phone input changed: $telephoneNumber")
                        }
                    },
                    label = { Text("Телефон") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && telephoneNumber.isEmpty()
                )
                if (showError && telephoneNumber.isEmpty()) {
                    Text(
                        text = "Телефон обязателен",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        showError = false
                        Log.d("AddressSelectionScreen", "Email input changed: $email")
                    },
                    label = { Text("E-mail") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = showError && email.isEmpty()
                )
                if (showError && email.isEmpty()) {
                    Text(
                        text = "E-mail обязателен",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            if (address.isNotEmpty() && name.isNotEmpty() && telephoneNumber.isNotEmpty() && email.isNotEmpty()) {
                                Log.d("AddressSelectionScreen", "Save button clicked")
                                storeViewModel.saveStore(address, name, telephoneNumber, email)
                                onSave()
                            } else {
                                showError = true
                            }
                        },
                        enabled = address.isNotEmpty() && name.isNotEmpty() && telephoneNumber.isNotEmpty() && email.isNotEmpty()
                    )
                    {
                        Text("Сохранить")
                    }
                    Button(onClick = {
                        Log.d("AddressSelectionScreen", "Skip button clicked")
                        onSkip()
                    }) {
                        Text("Пропустить")
                    }
                }
            }
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun AddressSelectionScreenPreview() {
//    AddressSelectionScreen(
//        onSave = {},
//        onSkip = {},
//        storeViewModel = null
//    )
//}