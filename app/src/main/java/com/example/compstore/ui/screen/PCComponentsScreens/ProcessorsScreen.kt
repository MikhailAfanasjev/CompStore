package com.example.compstore.ui.screen.PCComponentsScreens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compstore.ui.components.MenuItemProduct
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.processors
import com.example.compstore.viewmodel.BasketViewModel

@Composable
fun ProcessorsScreen(basketViewModel: BasketViewModel = hiltViewModel()) {
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    LazyColumn {
        items(processors) { product ->
            MenuItemProduct(
                name = product.name,
                price = product.price,
                imageResId = product.imageResId,
                onClick = { selectedProduct = product },
                onAddToCartClick = { basketViewModel.addToCart(product.productId) }
            )
        }
    }

    selectedProduct?.let { product ->
        ProductDetailsDialog(
            product = product,
            onDismiss = { selectedProduct = null },
            onAddToCart = { basketViewModel.addToCart(it.productId) }
        )
    }
}