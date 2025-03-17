package com.example.compstore.ui.screen.PCComponentsScreens.DataClasses

import com.example.compstore.ui.screen.PCComponentsScreens.Product
import com.example.linguareader.R

data class SSD(
    override val productId: Int,
    override val name: String,
    override val price: String,
    override val imageResId: Int?,
    override val description: String
) : Product

val ssds = listOf(
    SSD(
        productId = 19001,
        name = "SSD Samsung 970 EVO Plus 1TB - NVMe, PCIe 3.0, до 3500 МБ/с",
        price = "9 999 ₽",
        imageResId = R.drawable.samsung_970_evo_plus,
        description = "Высокопроизводительный NVMe SSD для максимальной скорости."
    ),
    SSD(
        productId = 19002,
        name = "SSD Kingston NV2 500GB - NVMe, PCIe 3.0, до 2200 МБ/с",
        price = "4 499 ₽",
        imageResId = R.drawable.kingston_nv2,
        description = "Оптимальное сочетание скорости и стоимости."
    )
)
