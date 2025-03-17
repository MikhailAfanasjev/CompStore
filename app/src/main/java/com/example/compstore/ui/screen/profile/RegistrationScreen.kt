package com.example.compstore.ui.screen.profile

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.compstore.ui.theme.PrimaryText
import com.example.compstore.utils.scaleDimension
import com.example.compstore.viewmodel.UserViewModel

@Composable
fun RegistrationScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundLightGrey
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaleDimension(16.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Регистрация",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = scaleDimension(16.dp)),
                color = PrimaryText
            )

            TextField(
                value = name,
                onValueChange = {
                    name = it
                    Log.d("RegistrationScreen", "Name input updated: $name")
                },
                label = { Text("ФИО", color = PrimaryText) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = PrimaryText)
            )
            Spacer(modifier = Modifier.height(scaleDimension(8.dp)))

            TextField(
                value = phone,
                onValueChange = {
                    phone = it
                    Log.d("RegistrationScreen", "Phone input updated: $phone")
                },
                label = { Text("Телефон", color = PrimaryText) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = PrimaryText)
            )
            Spacer(modifier = Modifier.height(scaleDimension(8.dp)))

            TextField(
                value = email,
                onValueChange = {
                    email = it
                    Log.d("RegistrationScreen", "Email input updated: $email")
                },
                label = { Text("Email", color = PrimaryText) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = PrimaryText)
            )
            Spacer(modifier = Modifier.height(scaleDimension(8.dp)))

            TextField(
                value = password,
                onValueChange = {
                    password = it
                    Log.d("RegistrationScreen", "Password input updated")
                },
                label = { Text("Пароль", color = PrimaryText) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = PrimaryText)
            )
            Spacer(modifier = Modifier.height(scaleDimension(16.dp)))

            // Кнопка "Зарегистрироваться"
            CustomButton(
                text = "Зарегистрироваться",
                onClick = {
                    Log.d("RegistrationScreen", "Register button clicked")
                    if (name.isNotEmpty() &&
                        phone.isNotEmpty() &&
                        email.isNotEmpty() &&
                        password.isNotEmpty()
                    ) {
                        Log.d("RegistrationScreen", "All fields are filled")
                        userViewModel.saveUser(name, phone, email, password)
                        Log.d("RegistrationScreen", "User saved successfully: name=$name, phone=$phone, email=$email")
                        Toast.makeText(context, "Регистрация успешна", Toast.LENGTH_SHORT).show()
                        navController.navigate("profile") {
                            popUpTo("registration") { inclusive = true }
                        }
                        Log.d("RegistrationScreen", "Navigated to 'profile' screen")
                    } else {
                        Log.w("RegistrationScreen", "Validation failed: some fields are empty")
                        Toast.makeText(context, "Заполните все поля", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}