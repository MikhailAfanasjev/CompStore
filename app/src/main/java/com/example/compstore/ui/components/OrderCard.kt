package com.example.compstore.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compstore.modelDB.Order
import com.example.compstore.ui.screen.PCComponentsScreens.Product
import com.example.linguareader.R

@Composable
fun OrderCard(
    order: Order,
    allProducts: List<Product>,
    onProductClick: (Product) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Заказ №${order.orderId}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Время заказа: ${order.orderTime}")
            Text(text = "Итоговая сумма: ${order.totalPrice}")
            Spacer(modifier = Modifier.height(16.dp))
            // Отображаем список товаров в заказе
            LazyRow {
                items(order.productIds) { productId ->
                    // Поиск товара по идентификатору в общем списке
                    val product = allProducts.find { it.productId == productId }
                    product?.let {
                        Image(
                            painter = painterResource(id = it.imageResId ?: R.drawable.ic_launcher_foreground),
                            contentDescription = it.name,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onProductClick(it) }
                                .padding(end = 8.dp)
                        )
                    }
                }
            }
        }
    }
}