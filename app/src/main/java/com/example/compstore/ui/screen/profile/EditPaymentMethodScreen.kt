package com.example.compstore.ui.screen.profile

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compstore.viewmodel.UserViewModel

@Composable
fun EditPaymentMethodScreen(navController: NavController, userViewModel: UserViewModel = hiltViewModel()) {
    var selectedPaymentMethod by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedPaymentMethod == "Оплата наличными",
                    onClick = {
                        selectedPaymentMethod = "Оплата наличными"
                        Log.d("EditPaymentMethod", "Selected: $selectedPaymentMethod")
                    }
                )
                Text(text = "Оплата наличными", modifier = Modifier.padding(start = 8.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedPaymentMethod == "Оплата картой",
                    onClick = {
                        selectedPaymentMethod = "Оплата картой"
                        Log.d("EditPaymentMethod", "Selected: $selectedPaymentMethod")
                    }
                )
                Text(text = "Оплата картой", modifier = Modifier.padding(start = 8.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedPaymentMethod == "Оплата переводом",
                    onClick = {
                        selectedPaymentMethod = "Оплата переводом"
                        Log.d("EditPaymentMethod", "Selected: $selectedPaymentMethod")
                    }
                )
                Text(text = "Оплата переводом", modifier = Modifier.padding(start = 8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (selectedPaymentMethod.isNotEmpty()) {
                        Log.d("EditPaymentMethod", "Saving payment method: $selectedPaymentMethod")
                        userViewModel.updatePaymentMethod(selectedPaymentMethod)
                        navController.popBackStack()
                    } else {
                        Log.d("EditPaymentMethod", "No payment method selected")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Сохранить")
            }
        }
    }
}