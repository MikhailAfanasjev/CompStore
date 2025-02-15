package com.example.compstore.ui.screen.PCComponentsScreens

interface Product {
    val productId: Int
    val name: String
    val price: String
    val imageResId: Int?
    val description: String
}