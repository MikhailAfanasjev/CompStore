package com.example.compstore.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compstore.viewmodel.StoreViewModel

@Composable
fun ProfileScreen(
    onSave: () -> Unit,
    storeViewModel: StoreViewModel = hiltViewModel()
) {
    val store by storeViewModel.store.observeAsState()
    rememberCoroutineScope()

    var address by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var telephoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        storeViewModel.getStore()
    }

    LaunchedEffect(store) {
        store?.let {
            address = it.address
            name = it.name
            telephoneNumber = it.telephoneNumber
            email = it.email
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = telephoneNumber,
                onValueChange = { telephoneNumber = it },
                label = { Text("Phone Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (address.isNotEmpty() && name.isNotEmpty() && telephoneNumber.isNotEmpty() && email.isNotEmpty()) {
                        Log.d("AddressSelectionScreen", "Save button clicked")
                        storeViewModel.saveStore(address, name, telephoneNumber, email)
                        onSave()
                    }
                },
                enabled = address.isNotEmpty() && name.isNotEmpty() && telephoneNumber.isNotEmpty() && email.isNotEmpty()
            ) {
                Text("Save")
            }
        }
    }
}