package com.example.compstore.ui.screen.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compstore.ui.components.CustomButton
import com.example.compstore.ui.theme.BackgroundLightGrey
import com.example.compstore.ui.theme.BrightOrange
import com.example.compstore.ui.theme.PrimaryText
import com.example.compstore.utils.scaleDimension
import com.example.compstore.utils.validateInputs
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
                text = "Редактировать данные",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = scaleDimension(16.dp)),
                color = PrimaryText
            )

            LabeledTextField(
                value = nameState.value,
                label = "ФИО",
                onValueChange = { nameState.value = it },
            )

            Spacer(modifier = Modifier.height(scaleDimension(8.dp)))

            LabeledTextField(
                value = phoneState.value,
                label = "Телефон",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                onValueChange = { phoneState.value = it }
            )

            Spacer(modifier = Modifier.height(scaleDimension(8.dp)))

            LabeledTextField(
                value = emailState.value,
                label = "Email",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { emailState.value = it }
            )

            Spacer(modifier = Modifier.height(scaleDimension(16.dp)))

            CustomButton(
                text = "Обновить",
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
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
        label = { Text(label, color = PrimaryText) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        textStyle = TextStyle(color = PrimaryText),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = BrightOrange
        )
    )
}