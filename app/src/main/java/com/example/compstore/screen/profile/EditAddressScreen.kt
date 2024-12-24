package com.example.compstore.screen.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compstore.viewmodel.AddressViewModel

@Composable
fun EditAddressScreen(
    navController: NavController,
    storeViewModel: AddressViewModel = hiltViewModel()
) {
    var city by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var house by remember { mutableStateOf("") }
    var apartment by remember { mutableStateOf("") }
    var isDialogOpen by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = {
            isDialogOpen = true
            Log.d("EditAddressScreen", "Add address button clicked, dialog opened")
        }) {
            Text("Добавить адрес")
        }

        if (isDialogOpen) {
            AlertDialog(
                onDismissRequest = {
                    isDialogOpen = false
                    Log.d("EditAddressScreen", "Dialog dismissed by user")
                },
                title = {
                    Text("Добавить новый адрес")
                    Log.d("EditAddressScreen", "Dialog displayed for adding a new address")
                },
                text = {
                    Column {
                        TextField(
                            value = city,
                            onValueChange = {
                                city = it
                                Log.d("EditAddressScreen", "City input updated: $city")
                            },
                            label = { Text("Город") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = street,
                            onValueChange = {
                                street = it
                                Log.d("EditAddressScreen", "Street input updated: $street")
                            },
                            label = { Text("Улица") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = house,
                            onValueChange = {
                                house = it
                                Log.d("EditAddressScreen", "House input updated: $house")
                            },
                            label = { Text("Дом") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        TextField(
                            value = apartment,
                            onValueChange = {
                                apartment = it
                                Log.d("EditAddressScreen", "Apartment input updated: $apartment")
                            },
                            label = { Text("Квартира") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (city.isNotEmpty() && street.isNotEmpty() && house.isNotEmpty() && apartment.isNotEmpty()) {
                                Log.d(
                                    "EditAddressScreen",
                                    "Saving address: city=$city, street=$street, house=$house, apartment=$apartment"
                                )
                                storeViewModel.saveUserAddress(city, street, house, apartment)
                                Toast.makeText(context, "Адрес успешно сохранен", Toast.LENGTH_SHORT).show()
                                Log.d("EditAddressScreen", "Address saved successfully")
                                isDialogOpen = false

                                navController.navigate("profile") {
                                    popUpTo("edit_address") { inclusive = true }
                                }
                                Log.d("EditAddressScreen", "Navigated to 'profile' screen")
                            } else {
                                Log.w("EditAddressScreen", "Validation failed: some fields are empty")
                                Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Text("Сохранить")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        isDialogOpen = false
                        Log.d("EditAddressScreen", "Dialog dismissed via 'Назад' button")
                    }) {
                        Text("Назад")
                    }
                }
            )
        }
    }
}