package com.example.compstore.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compstore.viewmodel.StoreViewModel

@Composable
fun EditAddressScreen(
    navController: NavController,
    storeViewModel: StoreViewModel = hiltViewModel()
) {
    var city by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var house by remember { mutableStateOf("") }
    var apartment by remember { mutableStateOf("") }
    var isDialogOpen by remember { mutableStateOf(false) }

    // Кнопка "Добавить адрес"
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = { isDialogOpen = true }) {
            Text("Добавить адрес")
        }

        // Диалоговое окно для ввода адреса
        if (isDialogOpen) {
            AlertDialog(
                onDismissRequest = { isDialogOpen = false },
                title = { Text("Добавить новый адрес") },
                text = {
                    Column {
                        TextField(
                            value = city,
                            onValueChange = { city = it },
                            label = { Text("Город") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = street,
                            onValueChange = { street = it },
                            label = { Text("Улица") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = house,
                            onValueChange = { house = it },
                            label = { Text("Дом") }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = apartment,
                            onValueChange = { apartment = it },
                            label = { Text("Квартира") }
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            // Сохранение данных в базу данных
                            storeViewModel.saveUserAddress(city, street, house, apartment)
                            isDialogOpen = false
                            navController.popBackStack()  // Возвращаемся назад
                        }
                    ) {
                        Text("Сохранить")
                    }
                },
                dismissButton = {
                    Button(onClick = { isDialogOpen = false }) {
                        Text("Назад")
                    }
                }
            )
        }
    }
}