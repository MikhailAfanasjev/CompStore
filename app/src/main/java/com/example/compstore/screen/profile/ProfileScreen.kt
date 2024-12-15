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
import com.example.compstore.viewmodel.StoreViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    storeViewModel: StoreViewModel = hiltViewModel()
) {
    val user by storeViewModel.user.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Log.d("ProfileScreen", "User loaded: ${user?.name ?: "No user data"}")

                Text(
                    text = "ФИО: ${user!!.name ?: "Не указано"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Телефон: ${user!!.telephoneNumber ?: "Не указано"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Email: ${user!!.email ?: "Не указано"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        Log.d("ProfileScreen", "Navigate to edit screen")
                        navController.navigate("edit")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Редактировать")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // История заказов
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Log.d("ProfileScreen", "History section clicked, user name: ${user?.name ?: "No name"}")
                Text(
                    text = "История заказов: ${user?.name ?: "Не указано"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = {
                        Log.d("ProfileScreen", "Navigate to edit history screen")
                        navController.navigate("editHistory")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Редактировать")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Способ оплаты
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Log.d("ProfileScreen", "Payment method section clicked")
                Text(
                    text = "Способ оплаты: ${user?.name ?: "Не указано"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = {
                        Log.d("ProfileScreen", "Navigate to edit payment method screen")
                        navController.navigate("editPaymentMethod")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Редактировать")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Сменить пароль
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Log.d("ProfileScreen", "Password change section clicked")
                Text(
                    text = "Сменить пароль: ${user?.name ?: "Не указано"}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = {
                        Log.d("ProfileScreen", "Navigate to edit password screen")
                        navController.navigate("editPassword")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Редактировать")
                }
            }
        }
    }
}