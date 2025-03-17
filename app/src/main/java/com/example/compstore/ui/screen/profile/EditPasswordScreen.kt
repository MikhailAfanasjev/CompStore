package com.example.compstore.ui.screen.profile

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compstore.ui.components.CustomButton
import com.example.compstore.ui.theme.AccentOrange
import com.example.compstore.ui.theme.BackgroundLightGrey
import com.example.compstore.ui.theme.BrightOrange
import com.example.compstore.ui.theme.PrimaryText
import com.example.compstore.utils.scaleDimension
import com.example.compstore.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPasswordScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val user by userViewModel.user.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundLightGrey
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaleDimension(16.dp)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Изменение пароля",
                style = MaterialTheme.typography.headlineSmall,
                color = PrimaryText
            )

            Spacer(modifier = Modifier.height(scaleDimension(16.dp)))

            OutlinedTextField(
                value = currentPassword,
                onValueChange = { currentPassword = it },
                label = { Text("Текущий пароль", color = PrimaryText) },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = PrimaryText)
            )

            Spacer(modifier = Modifier.height(scaleDimension(8.dp)))

            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("Новый пароль", color = PrimaryText) },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = PrimaryText)
            )

            Spacer(modifier = Modifier.height(scaleDimension(8.dp)))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Подтверждение пароля", color = PrimaryText) },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = PrimaryText),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = BrightOrange
                )
            )

            Spacer(modifier = Modifier.height(scaleDimension(8.dp)))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { showPassword = !showPassword }) {
                    Text(
                        text = if (showPassword) "Скрыть пароль" else "Показать пароль",
                        color = AccentOrange
                    )
                }
            }

            Spacer(modifier = Modifier.height(scaleDimension(16.dp)))

            CustomButton(
                text = "Сохранить",
                onClick = {
                    if (newPassword == confirmPassword && user != null) {
                        userViewModel.updateUser(
                            id = user!!.id,
                            name = user!!.name,
                            phone = user!!.telephoneNumber,
                            email = user!!.email,
                            password = newPassword
                        )
                        Log.d("EditPasswordScreen", "Password updated successfully")
                        navController.popBackStack()
                    } else {
                        Log.e("EditPasswordScreen", "Passwords do not match or user is null")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(scaleDimension(16.dp)))

            CustomButton(
                text = "Отмена",
                onClick = {
                    Log.d("EditPasswordScreen", "Navigating back")
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}