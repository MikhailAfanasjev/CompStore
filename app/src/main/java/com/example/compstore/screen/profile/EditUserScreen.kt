package com.example.compstore.screen.profile

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compstore.viewmodel.UserViewModel
@Composable
fun EditScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current

    val store by userViewModel.user.collectAsState()
    LaunchedEffect(store) {
        store?.let {
            name = it.name ?: ""
            phone = it.telephoneNumber ?: ""
            email = it.email ?: ""
        }
    }

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
            Text(
                text = "Редактировать данные",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            TextField(
                value = name,
                onValueChange = {
                    name = it
                    Log.d("EditScreen", "Name input updated: $name")
                },
                label = { Text("ФИО") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = phone,
                onValueChange = {
                    phone = it
                    Log.d("EditScreen", "Phone input updated: $phone")
                },
                label = { Text("Телефон") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = email,
                onValueChange = {
                    email = it
                    Log.d("EditScreen", "Email input updated: $email")
                },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    Log.d("EditScreen", "Save button clicked")
                    if (name.isNotEmpty() &&
                        phone.isNotEmpty() &&
                        email.isNotEmpty()
                    ) {
                        Log.d(
                            "EditScreen",
                            "Saving user: name=$name, phone=$phone, email=$email"
                        )

                        store?.let {
                            userViewModel.updateUser(
                                it.id,
                                name,
                                phone,
                                email,
                                it.password.orEmpty()
                            )
                        }

                        Log.d("EditUserScreen", "User updated successfully: name=$name, phone=$phone, email=$email")
                        Toast.makeText(context, "Данные успешно обновлены", Toast.LENGTH_SHORT).show()

                        navController.navigate("profile") {
                            popUpTo("registration") { inclusive = true }
                        }
                    } else {
                        Log.w("EditScreen", "Validation failed: some fields are empty")
                        Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Сохранить")
            }
        }
    }
}