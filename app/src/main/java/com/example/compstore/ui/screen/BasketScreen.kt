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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compstore.ui.components.BasketItem
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.coolers
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.gpus
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.motherboards
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.processors
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.psus
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.ramModules
import com.example.compstore.ui.screen.PCComponentsScreens.Product
import com.example.compstore.utils.formatPrice
import com.example.compstore.viewmodel.BasketViewModel
import com.example.compstore.viewmodel.OrderHistoryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketScreen(
    basketViewModel: BasketViewModel = hiltViewModel(),
    orderHistoryViewModel: OrderHistoryViewModel = hiltViewModel(), // Добавляем и инициализируем ViewModel
    onProductClick: (Product) -> Unit,
    onRemoveFromCart: (Product) -> Unit,
    onBuyClick: () -> Unit = {}
) {
    // Собираем все доступные категории товаров
    val allProducts: List<Product> = processors + gpus + psus + motherboards + coolers + ramModules

    val userId by basketViewModel.userId.collectAsState()

    val basketItemsFlow = if (userId == null) {
        basketViewModel.localCartItems
    } else {
        basketViewModel.cartItems
    }

    val basketItemsList by basketItemsFlow.collectAsState()

    // Преобразуем элементы корзины: находим соответствующий продукт и его количество
    val basketItems = basketItemsList
        .distinctBy { it.productId }
        .mapNotNull { cartItem ->
            val foundProduct = allProducts.find { it.productId == cartItem.productId }
            foundProduct?.let { product -> product to cartItem.quantity }
        }

    // Вычисляем общую сумму корзины.
    val totalSum = basketItems.sumOf { (product, quantity) ->
        product.price.filter { it.isDigit() }.toInt() * quantity
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Корзина", style = MaterialTheme.typography.titleLarge) },
                modifier = Modifier.fillMaxWidth()
            )
        },
        bottomBar = {
            if (basketItems.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = formatPrice(totalSum),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Button(
                            onClick = {
                                orderHistoryViewModel.completePurchase(allProducts)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Купить")
                        }
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
                    Text("Ваша корзина пуста", style = MaterialTheme.typography.titleMedium)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    itemsIndexed(basketItems) { _, item ->
                        val (product, quantity) = item

                        BasketItem(
                            name = product.name,
                            price = product.price,
                            imageResId = product.imageResId,
                            quantity = quantity,
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