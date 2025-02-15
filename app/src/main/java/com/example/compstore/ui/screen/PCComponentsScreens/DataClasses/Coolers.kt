package com.example.compstore.ui.screen.PCComponentsScreens.DataClasses

import com.example.compstore.ui.screen.PCComponentsScreens.Product
import com.example.linguareader.R

data class Cooler(
    override val productId: Int,
    override val name: String,
    override val price: String,
    override val imageResId: Int?,
    override val description: String
) : Product

val coolers = listOf(
    Cooler(
        productId = 16001,
        name = "Кулер для процессора Noctua NH-D15 - 1200–1500 об/мин, TDP 220 Вт, вентиляторы 2 х 140 мм, уровень шума 24.6 дБ",
        price = "10 999 ₽",
        imageResId = R.drawable.noctua_nh_d15,
        description = "Эффективное охлаждение для мощных процессоров с низким уровнем шума."
    ),
    Cooler(
        productId = 16002,
        name = "Кулер для процессора DeepCool GAMMAXX 400 - 1300 об/мин, TDP 180 Вт, вентилятор 120 мм, уровень шума 21 дБ",
        price = "2 499 ₽",
        imageResId = R.drawable.deepcool_gammaxx_400,
        description = "Доступное решение для эффективного охлаждения."
    )
)