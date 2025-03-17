package com.example.compstore.ui.screen.profile

import android.util.Log
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compstore.modelDB.Address
import com.example.compstore.ui.components.CustomButton
import com.example.compstore.ui.theme.AccentOrange
import com.example.compstore.ui.theme.BackgroundLightGrey
import com.example.compstore.ui.theme.BrightOrange
import com.example.compstore.ui.theme.CardBackground
import com.example.compstore.ui.theme.ErrorRed
import com.example.compstore.ui.theme.PrimaryText
import com.example.compstore.utils.scaleDimension
import com.example.compstore.viewmodel.AddressViewModel
import com.example.compstore.viewmodel.UserViewModel

@Composable
fun EditAddressScreen(
    navController: NavController,
    addressViewModel: AddressViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    var city by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var house by remember { mutableStateOf("") }
    var apartment by remember { mutableStateOf("") }
    var isDialogOpen by remember { mutableStateOf(false) }
    var editingAddressId by remember { mutableStateOf<Int?>(null) }
    val context = LocalContext.current

    val user by userViewModel.user.collectAsState()
    val userAddresses by addressViewModel.userAddresses.collectAsState()

    LaunchedEffect(user) {
        user?.let {
            Log.d("EditAddressScreen", "User ID set: ${it.id}")
            addressViewModel.setUserId(it.id)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundLightGrey
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaleDimension(16.dp))
        ) {
            LazyColumn {
                items(userAddresses) { address ->
                    CurrentAddressField(
                        address = address,
                        onEditClick = {
                            Log.d("EditAddressScreen", "Edit clicked for address ID: ${address.id}")
                            city = address.city
                            street = address.street
                            house = address.house
                            apartment = address.apartment
                            editingAddressId = address.id
                            isDialogOpen = true
                        },
                        onDeleteClick = {
                            Log.d("EditAddressScreen", "Delete clicked for address ID: ${address.id}")
                            addressViewModel.deleteAddress(address)
                            Toast.makeText(context, "Адрес удалён", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(scaleDimension(16.dp)))

            CustomButton(
                text = "Добавить адрес",
                onClick = {
                    Log.d("EditAddressScreen", "Add address button clicked")
                    city = ""
                    street = ""
                    house = ""
                    apartment = ""
                    isDialogOpen = true
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (isDialogOpen) {
                Log.d("EditAddressScreen", "Address input dialog opened")
                AddressInputDialog(
                    city = city,
                    street = street,
                    house = house,
                    apartment = apartment,
                    onCityChange = {
                        Log.d("EditAddressScreen", "City changed to: $it")
                        city = it
                    },
                    onStreetChange = {
                        Log.d("EditAddressScreen", "Street changed to: $it")
                        street = it
                    },
                    onHouseChange = {
                        Log.d("EditAddressScreen", "House changed to: $it")
                        house = it
                    },
                    onApartmentChange = {
                        Log.d("EditAddressScreen", "Apartment changed to: $it")
                        apartment = it
                    },
                    onCancel = {
                        Log.d("EditAddressScreen", "Dialog cancelled")
                        isDialogOpen = false
                    },
                    onSave = {
                        Log.d("EditAddressScreen", "Save button clicked")
                        if (city.isNotEmpty() && street.isNotEmpty() && house.isNotEmpty() && apartment.isNotEmpty()) {
                            val currentUserId = user?.id
                            if (currentUserId != null) {
                                if (editingAddressId != null) {
                                    Log.d(
                                        "EditAddressScreen",
                                        "Updating address ID: $editingAddressId with values: $city, $street, $house, $apartment"
                                    )
                                    addressViewModel.updateAddress(
                                        editingAddressId!!,
                                        city,
                                        street,
                                        house,
                                        apartment,
                                        currentUserId
                                    )
                                    Toast.makeText(context, "Адрес обновлён", Toast.LENGTH_SHORT).show()
                                } else {
                                    Log.d(
                                        "EditAddressScreen",
                                        "Saving new address with values: $city, $street, $house, $apartment"
                                    )
                                    addressViewModel.saveUserAddress(
                                        city = city,
                                        street = street,
                                        house = house,
                                        apartment = apartment,
                                        currentUserId
                                    )
                                    Toast.makeText(context, "Адрес сохранён", Toast.LENGTH_SHORT).show()
                                }
                                isDialogOpen = false
                            } else {
                                Log.w("EditAddressScreen", "User not found, cannot save address")
                                Toast.makeText(context, "Пользователь не найден", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Log.w("EditAddressScreen", "Fields are empty, cannot save address")
                            Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
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
        label = { Text("Текущий адрес", color = PrimaryText) },
        readOnly = true,
        trailingIcon = {
            Row {
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Редактировать",
                        tint = AccentOrange
                    )
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Удалить",
                        tint = ErrorRed
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = PrimaryText)
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
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
                .padding(scaleDimension(16.dp)),
            color = CardBackground
        ) {
            Column(
                modifier = Modifier
                    .padding(scaleDimension(16.dp))
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Введите адрес",
                    style = MaterialTheme.typography.titleLarge,
                    color = PrimaryText
                )
                Spacer(modifier = Modifier.height(scaleDimension(16.dp)))

                OutlinedTextField(
                    value = city,
                    onValueChange = onCityChange,
                    label = { Text("Город", color = PrimaryText) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = PrimaryText),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = BrightOrange
                    )
                )
                Spacer(modifier = Modifier.height(scaleDimension(8.dp)))

                OutlinedTextField(
                    value = street,
                    onValueChange = onStreetChange,
                    label = { Text("Улица", color = PrimaryText) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = PrimaryText),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = BrightOrange
                    )
                )
                Spacer(modifier = Modifier.height(scaleDimension(8.dp)))

                OutlinedTextField(
                    value = house,
                    onValueChange = onHouseChange,
                    label = { Text("Дом", color = PrimaryText) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = PrimaryText),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = BrightOrange
                    )
                )
                Spacer(modifier = Modifier.height(scaleDimension(8.dp)))

                OutlinedTextField(
                    value = apartment,
                    onValueChange = onApartmentChange,
                    label = { Text("Квартира", color = PrimaryText) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = PrimaryText),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = BrightOrange
                    )
                )
                Spacer(modifier = Modifier.height(scaleDimension(16.dp)))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CustomButton(
                        text = "Отмена",
                        onClick = onCancel,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(scaleDimension(8.dp)))
                    CustomButton(
                        text = "Сохранить",
                        onClick = onSave,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}