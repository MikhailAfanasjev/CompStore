package com.example.compstore.ui.screen.PCComponentsScreens.DataClasses

import com.example.compstore.ui.screen.PCComponentsScreens.Product
import com.example.linguareader.R

data class Case(
    override val productId: Int,
    override val name: String,
    override val price: String,
    override val imageResId: Int?,
    override val description: String
) : Product

val cases = listOf(
    Case(
        productId = 17001,
        name = "Корпус DeepCool CH560 Digital - ATX, стеклянная панель, 4 вентилятора в комплекте",
        price = "8 999 ₽",
        imageResId = R.drawable.deepcool_ch560_digital,
        description = "Просторный корпус с хорошей вентиляцией и цифровым дисплеем."
    ),
    Case(
        productId = 17002,
        name = "Корпус NZXT H510 - Mid Tower, закаленное стекло, без БП",
        price = "6 499 ₽",
        imageResId = R.drawable.nzxt_h510,
        description = "Стильный и компактный корпус с продуманным кабель-менеджментом."
    )
)