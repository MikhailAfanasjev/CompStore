package com.example.compstore.screen.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(
    navController: NavController
) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    val context = LocalContext.current

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
                value = login,
                onValueChange = {
                    login = it
                    Log.d("LoginScreen", "Login input changed: $login")
                },
                label = { Text("Номер телефона") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = password,
                onValueChange = {
                    password = it
                    Log.d("LoginScreen", "Password input changed: ${if (it.isNotEmpty()) "****" else ""}")
                },
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = {
                        rememberMe = it
                        Log.d("LoginScreen", "Remember Me checked: $rememberMe")
                    }
                )
                Text(text = "Запомнить меня", modifier = Modifier.padding(start = 8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if (login.isNotEmpty() && password.isNotEmpty()) {
                            Log.d("LoginScreen", "User logged in with login: $login")
                            Toast.makeText(context, "Вход выполнен", Toast.LENGTH_SHORT).show()
                            navController.navigate("profile")
                        } else {
                            Log.d("LoginScreen", "Login or password is empty")
                            Toast.makeText(context, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
                        }
                    },
                    enabled = login.isNotEmpty() && password.isNotEmpty()
                ) {
                    Text("Войти")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        Log.d("LoginScreen", "Navigating to Registration screen")
                        navController.navigate("registration")
                    }
                ) {
                    Text("Зарегистрироваться")
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewLoginScreen() {
//
//}