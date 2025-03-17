package com.example.compstore.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compstore.ui.theme.AccentOrange
import com.example.compstore.ui.theme.CardBackground
import com.example.compstore.ui.theme.ErrorRed
import com.example.compstore.ui.theme.PrimaryText

@Composable
fun BasketItem(
    name: String,
    price: String,
    imageResId: Int?,
    quantity: Int,
    isChecked: Boolean, // Отметка товара
    onCheckedChange: (Boolean) -> Unit, // Колбэк для изменения состояния галочки
    onClick: () -> Unit,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit,
    onRemoveClick: () -> Unit  // Полное удаление товара из корзины
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Галочка для отметки товара
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
            Spacer(modifier = Modifier.width(8.dp))
            // Изображение продукта
            imageResId?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            // Название продукта, цена и управление количеством
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium.copy(color = PrimaryText),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = price,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        ),
                        color = AccentOrange,
                        textAlign = TextAlign.End
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                // Управление количеством товара: уменьшение и увеличение
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(40.dp)) {
                        CustomButton(
                            text = "-",
                            onClick = onDecreaseClick,
                            modifier = Modifier
                                .fillMaxSize()
                                .scale(0.7f)
                        )
                    }
                    Text(
                        text = quantity.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Box(modifier = Modifier.size(40.dp)) {
                        CustomButton(
                            text = "+",
                            onClick = onIncreaseClick,
                            modifier = Modifier
                                .fillMaxSize()
                                .scale(0.7f)
                        )
                    }
                }
            }
            IconButton(
                onClick = onRemoveClick,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Удалить товар",
                    tint = ErrorRed
                )
            }
        }
    }
}