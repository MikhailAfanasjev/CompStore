package com.example.compstore.ui.screen.PCComponentsScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compstore.ui.components.MenuItemProduct
import com.example.compstore.ui.components.ProductDetailsDialog
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.motherboards
import com.example.compstore.utils.scaleDimension
import com.example.compstore.viewmodel.BasketViewModel

@Composable
fun MotherboardsScreen(basketViewModel: BasketViewModel = hiltViewModel()) {
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    LazyColumn(
        modifier = Modifier.padding(scaleDimension(16.dp)),
        contentPadding = PaddingValues(scaleDimension(8.dp)),
        verticalArrangement = Arrangement.spacedBy(scaleDimension(8.dp))
    ) {
        items(motherboards) { product ->
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