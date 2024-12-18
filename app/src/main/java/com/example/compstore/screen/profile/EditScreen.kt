package com.example.compstore.screen.profile

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compstore.viewmodel.StoreViewModel

@Composable
fun EditScreen(
    navController: NavController,
    storeViewModel: StoreViewModel = hiltViewModel()
) {
    val store by storeViewModel.user.collectAsState()

    val nameState = remember { mutableStateOf(store?.name ?: "") }
    val phoneState = remember { mutableStateOf(store?.telephoneNumber ?: "") }
    val emailState = remember { mutableStateOf(store?.email ?: "") }
    var password by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text("Редактировать данные", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nameState.value,
                onValueChange = {
                    nameState.value = it
                    Log.d("EditScreen", "Name changed: $it")
                },
                label = { Text("ФИО") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = phoneState.value,
                onValueChange = {
                    phoneState.value = it
                    Log.d("EditScreen", "Phone changed: $it")
                },
                label = { Text("Телефон") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = emailState.value,
                onValueChange = {
                    emailState.value = it
                    Log.d("EditScreen", "Email changed: $it")
                },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    Log.d("EditScreen", "Save button clicked")
                    storeViewModel.saveUser(
                        name = nameState.value,
                        phone = phoneState.value,
                        email = emailState.value,
                        password = password
                    )
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Сохранить")
            }
        }
    }
}