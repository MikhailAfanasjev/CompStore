package com.example.compstore.ui.screen.profile

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compstore.ui.components.CustomButton
import com.example.compstore.ui.theme.BackgroundLightGrey
import com.example.compstore.ui.theme.PrimaryText
import com.example.compstore.utils.scaleDimension
import com.example.compstore.viewmodel.AddressViewModel
import com.example.compstore.viewmodel.UserViewModel


@Composable
fun ProfileScreen(
    navController: NavController,
    addressViewModel: AddressViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    // Используем поток loggedInUser в качестве источника данных
    val user by userViewModel.loggedInUser.collectAsState()
    val userAddresses by addressViewModel.userAddresses.collectAsState()
    val paymentMethod by userViewModel.paymentMethod.collectAsState()

    LaunchedEffect(user) {
        user?.let {
            Log.d("ProfileScreen", "User ID set: ${it.id}")
            addressViewModel.setUserId(it.id)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundLightGrey // Основной фон приложения
    ) {
        if (user == null) {
            Log.d("ProfileScreen", "User data is loading...")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaleDimension(16.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Данные пользователя загружаются...",
                    style = MaterialTheme.typography.bodyMedium.copy(color = PrimaryText)
                )
            }
        } else {
            Log.d("ProfileScreen", "User data loaded: $user")
            Log.d("ProfileScreen", "Address data loaded: $userAddresses")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaleDimension(16.dp)),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                // Информация о пользователе
                Text(
                    text = "ФИО: ${user?.name ?: "Не указано"}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = PrimaryText)
                )
                Spacer(modifier = Modifier.height(scaleDimension(8.dp)))

                Text(
                    text = "Телефон: ${user?.telephoneNumber ?: "Не указано"}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = PrimaryText)
                )
                Spacer(modifier = Modifier.height(scaleDimension(8.dp)))

                Text(
                    text = "Email: ${user?.email ?: "Не указано"}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = PrimaryText)
                )
                Spacer(modifier = Modifier.height(scaleDimension(16.dp)))

                // Кнопка для редактирования данных пользователя
                CustomButton(
                    text = "Редактировать",
                    onClick = {
                        Log.d("ProfileScreen", "Navigating to Edit Screen")
                        navController.navigate("edit")
                    }
                )
                Spacer(modifier = Modifier.height(scaleDimension(16.dp)))

                // Адрес пользователя
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (userAddresses.isEmpty()) {
                        Log.d("AddressSection", "User addresses list is empty")
                        Text(
                            text = "Адрес не указан",
                            style = MaterialTheme.typography.bodyMedium.copy(color = PrimaryText)
                        )
                    } else {
                        Log.d("AddressSection", "User addresses count: ${userAddresses.size}")
                        userAddresses.forEach { address ->
                            Log.d(
                                "AddressSection",
                                "Address: ${address.city}, ${address.street}, ${address.house}, ${address.apartment}"
                            )
                            Text(
                                text = "${address.city}, ${address.street}, ${address.house}, ${address.apartment}",
                                style = MaterialTheme.typography.bodyMedium.copy(color = PrimaryText)
                            )
                            Spacer(modifier = Modifier.height(scaleDimension(4.dp)))
                        }
                    }
                    Spacer(modifier = Modifier.height(scaleDimension(4.dp)))

                    // Кнопка для редактирования адреса
                    CustomButton(
                        text = "Редактировать",
                        onClick = {
                            Log.d("ProfileScreen", "Navigating to Edit Address Screen")
                            navController.navigate("editAddress")
                        }
                    )
                }

                Spacer(modifier = Modifier.height(scaleDimension(16.dp)))

                // История заказов
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Log.d("ProfileScreen", "Opening Order History Section")
                    Text(
                        text = "История заказов:",
                        style = MaterialTheme.typography.bodyMedium.copy(color = PrimaryText)
                    )
                    Spacer(modifier = Modifier.height(scaleDimension(4.dp)))

                    CustomButton(
                        text = "История заказов",
                        onClick = {
                            Log.d("ProfileScreen", "Navigating to History Screen")
                            navController.navigate("historyScreen")
                        }
                    )
                }

                Spacer(modifier = Modifier.height(scaleDimension(16.dp)))

                // Способ оплаты
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Способ оплаты: ${if (paymentMethod.isEmpty()) "Не указано" else paymentMethod}",
                        style = MaterialTheme.typography.bodyMedium.copy(color = PrimaryText)
                    )
                    Spacer(modifier = Modifier.height(scaleDimension(4.dp)))

                    CustomButton(
                        text = "Редактировать",
                        onClick = {
                            navController.navigate("editPaymentMethod")
                        }
                    )
                }

                Spacer(modifier = Modifier.height(scaleDimension(16.dp)))

                // Смена пароля
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Log.d("ProfileScreen", "Opening Password Change Section")
                    CustomButton(
                        text = "Сменить пароль",
                        onClick = {
                            Log.d("ProfileScreen", "Navigating to Edit Password Screen")
                            navController.navigate("editPassword")
                        }
                    )
                }

                Spacer(modifier = Modifier.height(scaleDimension(16.dp)))

                // Кнопка выхода из профиля
                CustomButton(
                    text = "Выход",
                    onClick = {
                        Log.d("ProfileScreen", "Logging out user")
                        userViewModel.logoutUser()
                        navController.navigate("login") {
                            popUpTo("profile") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}