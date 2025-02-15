package com.example.compstore.ui.screen.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compstore.ui.components.OrderCard
import com.example.compstore.viewmodel.OrderHistoryViewModel

@Composable
fun HistoryScreen(
    viewModel: OrderHistoryViewModel = hiltViewModel()
) {
    val orders by viewModel.orders.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(orders) { order ->
            OrderCard(order = order) {
                // будет переход к деталям заказа
            }
        }
    }
}