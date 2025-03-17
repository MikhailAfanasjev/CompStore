package com.example.compstore.ui.screen.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compstore.ui.components.CustomButton
import com.example.compstore.ui.theme.AccentOrange
import com.example.compstore.ui.theme.BackgroundLightGrey
import com.example.compstore.ui.theme.BrightOrange
import com.example.compstore.ui.theme.CardBackground
import com.example.compstore.ui.theme.PrimaryText
import com.example.compstore.utils.scaleDimension
import kotlinx.coroutines.launch
import com.example.compstore.viewmodel.UserViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
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
        color = BackgroundLightGrey
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(scaleDimension(16.dp))
        ) {
            TextField(
                value = login,
                onValueChange = { login = it },
                label = { Text("Номер телефона", color = PrimaryText) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = PrimaryText),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = BrightOrange
                )
            )
            Spacer(modifier = Modifier.height(scaleDimension(8.dp)))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Пароль", color = PrimaryText) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = PrimaryText),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = BrightOrange
                )
            )
            Spacer(modifier = Modifier.height(scaleDimension(8.dp)))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = rememberMe,
                    onCheckedChange = { rememberMe = it },
                    colors = CheckboxDefaults.colors(checkedColor = AccentOrange)
                )
                Text(
                    text = "Запомнить меня",
                    modifier = Modifier.padding(start = scaleDimension(8.dp)),
                    color = PrimaryText
                )
            }
            Spacer(modifier = Modifier.height(scaleDimension(8.dp)))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Кнопка "Войти"
                CustomButton(
                    text = "Войти",
                    onClick = {
                        if (login.isNotEmpty() && password.isNotEmpty()) {
                            coroutineScope.launch {
                                val isAuthenticated = userViewModel.authenticateUser(login, password)
                                if (isAuthenticated) {
                                    userViewModel.loginUser(login, rememberMe)
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
                    modifier = Modifier.width(scaleDimension(100.dp))
                )

                Spacer(modifier = Modifier.width(scaleDimension(8.dp)))

                // Кнопка "Зарегистрироваться"
                CustomButton(
                    text = "Зарегистрироваться",
                    onClick = {
                        Log.d("LoginScreen", "Navigating to Registration screen")
                        navController.navigate("registration")
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}