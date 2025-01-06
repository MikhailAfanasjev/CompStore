package com.example.compstore.screen.profile

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compstore.viewmodel.AddressViewModel
import com.example.compstore.viewmodel.UserViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    storeViewModel: AddressViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {
    val user by userViewModel.user.collectAsState()
    val userAddresses by storeViewModel.userAddresses.collectAsState()
    val paymentMethod by userViewModel.paymentMethod.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (user == null) {
            Log.d("ProfileScreen", "User data is loading...")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Данные пользователя загружаются...",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            Log.d("ProfileScreen", "User data loaded: $user")
            Log.d("ProfileScreen", "Address data loaded: $userAddresses")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                // User
                Text(
                    text = "ФИО: ${user?.name ?: "Не указано"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Телефон: ${user?.telephoneNumber ?: "Не указано"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Email: ${user?.email ?: "Не указано"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        Log.d("ProfileScreen", "Navigating to Edit Screen")
                        navController.navigate("edit")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Редактировать")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Address
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (userAddresses.isEmpty()) {
                        Text(
                            text = "Адрес не указан",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    } else {
                        userAddresses.forEach { address ->
                            Text(
                                text = "${address.city}, ${address.street}, ${address.house}, ${address.apartment}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick = {
                            Log.d("ProfileScreen", "Navigating to Edit Address Screen")
                            navController.navigate("editAddress")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Редактировать")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // History
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Log.d("ProfileScreen", "Opening Order History Section")
                    Text(
                        text = "История заказов: ${user?.name ?: "Не указано"}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick = {
                            Log.d("ProfileScreen", "Navigating to Edit History Screen")
                            navController.navigate("editHistory")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Редактировать")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Payment Method
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Способ оплаты: $paymentMethod",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = {
                            navController.navigate("editPaymentMethod")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Редактировать")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Password
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Log.d("ProfileScreen", "Opening Password Change Section")
                    Button(
                        onClick = {
                            Log.d("ProfileScreen", "Navigating to Edit Password Screen")
                            navController.navigate("editPassword")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Сменить пароль")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Logout Button
                Button(
                    onClick = {
                        Log.d("ProfileScreen", "Logging out user")
                        userViewModel.logoutUser()
                        navController.navigate("login") {
                            popUpTo("profile") { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Выход")
                }
            }
        }
    }
}