package com.example.compstore.ui.screen.profile

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.example.compstore.viewmodel.UserViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first

@Composable
fun LoginScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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
                onValueChange = { login = it },
                label = { Text("Номер телефона") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if (login.isNotEmpty() && password.isNotEmpty()) {
                            coroutineScope.launch {
                                val isAuthenticated = userViewModel.authenticateUser(login, password)
                                if (isAuthenticated) {
                                    userViewModel.loginUser(login)
                                    // Ждём, пока данные пользователя не будут загружены (т.е. user станет ненулевым)
                                    userViewModel.user.filter { it != null }.first()
                                    Toast.makeText(context, "Вход выполнен", Toast.LENGTH_SHORT).show()
                                    navController.navigate("profile")
                                } else {
                                    Toast.makeText(context, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
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