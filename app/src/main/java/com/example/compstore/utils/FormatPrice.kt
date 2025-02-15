package com.example.compstore.utils

import java.text.DecimalFormat

fun formatPrice(price: Int): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(price) + " ₽"
}