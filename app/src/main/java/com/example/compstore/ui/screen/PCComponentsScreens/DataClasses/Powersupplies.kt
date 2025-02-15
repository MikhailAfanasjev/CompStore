package com.example.compstore.ui.screen.PCComponentsScreens.DataClasses

import com.example.compstore.ui.screen.PCComponentsScreens.Product
import com.example.linguareader.R

data class PSU(
    override val productId: Int,
    override val name: String,
    override val price: String,
    override val imageResId: Int?,
    override val description: String
) : Product

val psus = listOf(
    PSU(
        productId = 15001,
        name = "Блок питания Cooler Master V850 Gold V2 - 850 Вт, 80+ Gold, модульный, ATX, вентилятор 135 мм",
        price = "14 999 ₽",
        imageResId = R.drawable.cooler_master_v850_gold_v2,
        description = "Надёжный блок питания с высоким КПД."
    ),
    PSU(
        productId = 15002,
        name = "Блок питания Corsair RM750 - 750 Вт, 80+ Gold, полностью модульный, ATX, вентилятор 135 мм",
        price = "11 499 ₽",
        imageResId = R.drawable.corsair_rm750,
        description = "Эффективный блок питания для стабильной работы системы."
    )
)