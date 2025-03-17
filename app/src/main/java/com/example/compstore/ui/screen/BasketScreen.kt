package com.example.compstore.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compstore.ui.components.BasketItem
import com.example.compstore.ui.components.CustomButton
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.cases
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.coolers
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.gpus
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.hdds
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.monitors
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.motherboards
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.processors
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.psus
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.ramModules
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.ssds
import com.example.compstore.ui.screen.PCComponentsScreens.Product
import com.example.compstore.ui.theme.AccentOrange
import com.example.compstore.ui.theme.BackgroundLightGrey
import com.example.compstore.ui.theme.CardBackground
import com.example.compstore.ui.theme.PrimaryText
import com.example.compstore.utils.formatPrice
import com.example.compstore.utils.scaleDimension
import com.example.compstore.viewmodel.BasketViewModel
import com.example.compstore.viewmodel.OrderHistoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketScreen(
    basketViewModel: BasketViewModel = hiltViewModel(),
    orderHistoryViewModel: OrderHistoryViewModel = hiltViewModel(),
    onProductClick: (Product) -> Unit,
    onRemoveFromCart: (Product) -> Unit,
    onBuyClick: () -> Unit = {}
) {
    // Собираем все доступные категории товаров
    val allProducts: List<Product> = processors + gpus + psus + motherboards + coolers + ramModules + cases + hdds + monitors + ssds

    val userId by basketViewModel.userId.collectAsState()

    val basketItemsFlow = if (userId == null) {
        basketViewModel.localCartItems
    } else {
        basketViewModel.cartItems
    }

    val basketItemsList by basketItemsFlow.collectAsState()

    val basketItems = basketItemsList
        .distinctBy { it.productId }
        .mapNotNull { cartItem ->
            val foundProduct = allProducts.find { it.productId == cartItem.productId }
            foundProduct?.let { product -> product to cartItem.quantity }
        }

    val selectedItems = remember { mutableStateListOf<Int>() }
    val selectedBasketItems = basketItems.filter { (product, _) ->
        selectedItems.contains(product.productId)
    }
    val totalSum = selectedBasketItems.sumOf { (product, quantity) ->
        product.price.filter { it.isDigit() }.toInt() * quantity
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundLightGrey
    ) {
        Scaffold(
            containerColor = Color.Transparent, // Делаем фон Scaffold прозрачным
            bottomBar = {
                if (selectedBasketItems.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(scaleDimension(16.dp))
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = formatPrice(totalSum),
                                style = MaterialTheme.typography.titleLarge,
                                color = PrimaryText
                            )
                            CustomButton(
                                text = "Купить",
                                onClick = {
                                    if (userId == null) {
                                        onBuyClick()
                                    } else {
                                        orderHistoryViewModel.completePurchase(allProducts, selectedItems.toList())
                                        selectedItems.clear()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                if (basketItems.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Ваша корзина пуста",
                            style = MaterialTheme.typography.titleMedium,
                            color = PrimaryText
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(scaleDimension(16.dp))
                    ) {
                        itemsIndexed(basketItems) { _, item ->
                            val (product, quantity) = item
                            BasketItem(
                                name = product.name,
                                price = product.price,
                                imageResId = product.imageResId,
                                quantity = quantity,
                                isChecked = selectedItems.contains(product.productId),
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        if (!selectedItems.contains(product.productId)) {
                                            selectedItems.add(product.productId)
                                        }
                                    } else {
                                        selectedItems.remove(product.productId)
                                    }
                                },
                                onClick = { onProductClick(product) },
                                onIncreaseClick = { basketViewModel.addToCart(product.productId) },
                                onDecreaseClick = { basketViewModel.removeFromCart(product.productId) },
                                onRemoveClick = { basketViewModel.removeProductFromCart(product.productId) }
                            )
                        }
                    }
                }
            }
        }
    }
}