package com.example.compstore.screen.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compstore.modelDB.Address
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

    val userAddresses by storeViewModel.userAddresses.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn {
            items(userAddresses) { address ->
                CurrentAddressField(
                    address = address,
                    onEditClick = {
                        city = address.city
                        street = address.street
                        house = address.house
                        apartment = address.apartment
                        isDialogOpen = true
                    },
                    onDeleteClick = {
                        storeViewModel.deleteAddress(address)
                        Toast.makeText(context, "Адрес удалён", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            city = ""
            street = ""
            house = ""
            apartment = ""
            isDialogOpen = true
        }) {
            Text("Добавить адрес")
        }

        if (isDialogOpen) {
            AddressInputDialog(
                city = city,
                street = street,
                house = house,
                apartment = apartment,
                onCityChange = { city = it },
                onStreetChange = { street = it },
                onHouseChange = { house = it },
                onApartmentChange = { apartment = it },
                onCancel = { isDialogOpen = false },
                onSave = {
                    if (city.isNotEmpty() && street.isNotEmpty() && house.isNotEmpty() && apartment.isNotEmpty()) {
                        storeViewModel.saveUserAddress(city, street, house, apartment)
                        Toast.makeText(context, "Адрес сохранён", Toast.LENGTH_SHORT).show()
                        isDialogOpen = false
                    } else {
                        Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}

@Composable
fun CurrentAddressField(
    address: Address,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    OutlinedTextField(
        value = "${address.city}, ${address.street}, ${address.house}, ${address.apartment}",
        onValueChange = {},
        label = { Text("Текущий адрес") },
        readOnly = true,
        trailingIcon = {
            Row {
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Редактировать"
                    )
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Удалить"
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun AddressInputDialog(
    city: String,
    street: String,
    house: String,
    apartment: String,
    onCityChange: (String) -> Unit,
    onStreetChange: (String) -> Unit,
    onHouseChange: (String) -> Unit,
    onApartmentChange: (String) -> Unit,
    onCancel: () -> Unit,
    onSave: () -> Unit
) {
    Dialog(onDismissRequest = onCancel) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Введите адрес", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = city,
                    onValueChange = onCityChange,
                    label = { Text("Город") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = street,
                    onValueChange = onStreetChange,
                    label = { Text("Улица") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = house,
                    onValueChange = onHouseChange,
                    label = { Text("Дом") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = apartment,
                    onValueChange = onApartmentChange,
                    label = { Text("Квартира") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onCancel) {
                        Text("Отмена")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = onSave) {
                        Text("Сохранить")
                    }
                }
            }
        }
    }
}