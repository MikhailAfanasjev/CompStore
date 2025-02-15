package com.example.compstore.ui.screen.PCComponentsScreens.DataClasses

import com.example.compstore.ui.screen.PCComponentsScreens.Product
import com.example.linguareader.R

data class Motherboard(
    override val productId: Int,
    override val name: String,
    override val price: String,
    override val imageResId: Int?,
    override val description: String
) : Product

val motherboards = listOf(
    Motherboard(
        productId = 12001,
        name = "Материнская плата ASUS ROG Crosshair X670E HERO - Socket AM5, ATX, DDR5, PCIe 5.0, Wi-Fi 6E, USB 4.0",
        price = "89 999 ₽",
        imageResId = R.drawable.rog_crosshair_x670e_extreme,
        description = "Премиальная материнская плата для игровых систем."
    ),
    Motherboard(
        productId = 12002,
        name = "Материнская плата MSI MAG B550M Mortar - Socket AM4, mATX, DDR4, PCIe 4.0, HDMI/DP, 2.5G LAN",
        price = "10 499 ₽",
        imageResId = R.drawable.msi_mag_b550m_mortar,
        description = "Отличное сочетание цены и качества для компактных сборок."
    )
)