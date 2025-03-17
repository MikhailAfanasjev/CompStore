package com.example.compstore.ui.screen.PCComponentsScreens.DataClasses

import com.example.compstore.ui.screen.PCComponentsScreens.Product
import com.example.linguareader.R

data class HDD(
    override val productId: Int,
    override val name: String,
    override val price: String,
    override val imageResId: Int?,
    override val description: String
) : Product

val hdds = listOf(
    HDD(
        productId = 18001,
        name = "Жесткий диск Seagate Barracuda 2TB - 7200 об/мин, кеш 256 МБ, SATA III",
        price = "5 499 ₽",
        imageResId = R.drawable.seagate_barracuda_2tb,
        description = "Надежный жесткий диск с высокой скоростью работы."
    ),
    HDD(
        productId = 18002,
        name = "Жесткий диск Western Digital Black 4TB - 7200 об/мин, кеш 256 МБ, SATA III",
        price = "10 999 ₽",
        imageResId = R.drawable.wd_black_4tb,
        description = "Производительный HDD для хранения больших объемов данных."
    )
)
