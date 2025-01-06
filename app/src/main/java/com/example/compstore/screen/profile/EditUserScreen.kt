package com.example.compstore.screen.profile

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compstore.viewmodel.UserViewModel

@Composable
fun EditUserScreen(
    navController: NavController,
    storeViewModelUser: UserViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val store by storeViewModelUser.user.collectAsState()
    val nameState = remember { mutableStateOf("") }
    val phoneState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }

    store?.let {
        nameState.value = it.name ?: ""
        phoneState.value = it.telephoneNumber ?: ""
        emailState.value = it.email ?: ""
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

            LabeledTextField(
                value = nameState.value,
                label = "ФИО",
                onValueChange = { nameState.value = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            LabeledTextField(
                value = phoneState.value,
                label = "Телефон",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                onValueChange = { phoneState.value = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            LabeledTextField(
                value = emailState.value,
                label = "Email",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { emailState.value = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (validateInputs(nameState.value, phoneState.value, emailState.value)) {
                        store?.let {
                            storeViewModelUser.updateUser(
                                it.id,
                                nameState.value,
                                phoneState.value,
                                emailState.value,
                                it.password.orEmpty()
                            )
                            Toast.makeText(context, "Данные успешно обновлены", Toast.LENGTH_SHORT).show()
                            navController.navigate("profile") {
                                popUpTo("registration") { inclusive = true }
                            }
                        }
                    } else {
                        Toast.makeText(context, "Заполните все поля корректно", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Обновить")
            }
        }
    }
}

@Composable
fun LabeledTextField(
    value: String,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions
    )
}

fun validateInputs(name: String, phone: String, email: String): Boolean {
    return name.isNotEmpty() &&
            phone.matches(Regex("\\+?[0-9]{10,15}")) && // Validate phone number
            email.matches(Regex("^[\\w-.]+@[\\w-]+\\.[a-z]{2,4}$")) // Validate email
}