package com.example.compstore.ui.screen.PCComponentsScreens.DataClasses

import com.example.compstore.ui.screen.PCComponentsScreens.Product
import com.example.linguareader.R

data class Monitor(
    override val productId: Int,
    override val name: String,
    override val price: String,
    override val imageResId: Int?,
    override val description: String
) : Product

val monitors = listOf(
    Monitor(
        productId = 20001,
        name = "Монитор LG UltraGear 27GN800 - 27'', IPS, 144 Гц, 1 мс",
        price = "24 999 ₽",
        imageResId = R.drawable.lg_ultragear_27gn800,
        description = "Игровой монитор с высокой частотой обновления и быстрым откликом."
    ),
    Monitor(
        productId = 20002,
        name = "Монитор Dell U2723QE - 27'', IPS, 4K, USB-C",
        price = "49 999 ₽",
        imageResId = R.drawable.dell_u2723qe,
        description = "Профессиональный 4K-монитор с точной цветопередачей."
    )
)