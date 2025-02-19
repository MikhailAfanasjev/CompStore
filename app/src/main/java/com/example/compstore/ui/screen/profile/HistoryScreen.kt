package com.example.compstore.ui.screen.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compstore.ui.components.OrderCard
import com.example.compstore.ui.screen.PCComponentsScreens.Product
import com.example.compstore.ui.screen.PCComponentsScreens.ProductDetailsDialog
import com.example.compstore.viewmodel.BasketViewModel
import com.example.compstore.viewmodel.OrderHistoryViewModel

@Composable
fun HistoryScreen(
    viewModel: OrderHistoryViewModel = hiltViewModel(),
    basketViewModel: BasketViewModel = hiltViewModel(), // Добавляем BasketViewModel
    allProducts: List<Product>
) {
    val orders by viewModel.orders.collectAsState()
    // Состояние для выбранного товара (для отображения диалога)
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(orders) { order ->
            OrderCard(order = order, allProducts = allProducts) { product ->
                selectedProduct = product
            }
        }
    }

    // Если выбран товар, отображаем диалог с его описанием
    selectedProduct?.let { product ->
        ProductDetailsDialog(
            product = product,
            onDismiss = { selectedProduct = null },
            onAddToCart = {
                basketViewModel.addToCart(product.productId) // Реализация добавления в корзину
            }
        )
    }
}