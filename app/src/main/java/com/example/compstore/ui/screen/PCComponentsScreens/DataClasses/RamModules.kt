package com.example.compstore.ui.screen.PCComponentsScreens.DataClasses

import com.example.compstore.ui.screen.PCComponentsScreens.Product
import com.example.linguareader.R

data class RAM(
    override val productId: Int,
    override val name: String,
    override val price: String,
    override val imageResId: Int?,
    override val description: String
) : Product

val ramModules = listOf(
    RAM(
        productId = 14001,
        name = "Оперативная память Kingston Fury Beast RGB 16 ГБ - DDR4, 3200 МГц, CL16, 1.35 В",
        price = "6 499 ₽",
        imageResId = R.drawable.kingston_fury_beast_rgb,
        description = "Высокоскоростная память с эффектной RGB подсветкой."
    ),
    RAM(
        productId = 14002,
        name = "Оперативная память Corsair Vengeance LPX 8 ГБ - DDR4, 3000 МГц, CL16, 1.2 В",
        price = "3 299 ₽",
        imageResId = R.drawable.corsair_vengeance_lpx,
        description = "Надёжная память для игровых и офисных сборок."
    )
)