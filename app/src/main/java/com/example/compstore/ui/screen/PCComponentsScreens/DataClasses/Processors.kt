package com.example.compstore.ui.screen.PCComponentsScreens.DataClasses

import com.example.compstore.ui.screen.PCComponentsScreens.Product
import com.example.linguareader.R

data class Processor(
    override val productId: Int,
    override val name: String,
    override val price: String,
    override val imageResId: Int?,
    override val description: String
) : Product

val processors = listOf(
    Processor(
        productId = 11001,
        name = "Процессор AMD Ryzen Threadripper PRO 5995WX - sWRX8, 64 x 2.7 ГГц, L2 - 32 МБ, L3 - 256 МБ, 8 х DDR4-3200 МГц, TDP 280 Вт",
        price = "549 999 ₽",
        imageResId = R.drawable.pro5995wx,
        description = "Высокопроизводительный процессор для профессиональных задач."
    ),
    Processor(
        productId = 11002,
        name = "Процессор AMD A6-7480 - FM2+, 2 x 3.5 ГГц, L2 - 1 МБ, 2 х DDR3-2133 МГц, AMD Radeon R5, TDP 65 Вт",
        price = "950 ₽",
        imageResId = R.drawable.ic_a6_7480,
        description = "Бюджетный процессор для базовых задач."
    )
)