package com.example.compstore.ui.screen.PCComponentsScreens.DataClasses

import com.example.compstore.ui.screen.PCComponentsScreens.Product
import com.example.linguareader.R

data class GPU(
    override val productId: Int,
    override val name: String,
    override val price: String,
    override val imageResId: Int?,
    override val description: String
) : Product

val gpus = listOf(
    GPU(
        productId = 13001,
        name = "Видеокарта NVIDIA GeForce RTX 4090 - 24 ГБ GDDR6X, 384-bit, PCIe 4.0, HDMI, DisplayPort",
        price = "159 999 ₽",
        imageResId = R.drawable.nvidia_rtx_4090,
        description = "Флагманская видеокарта NVIDIA для требовательных задач и игр на ультра-настройках."
    ),
    GPU(
        productId = 13002,
        "Видеокарта MSI Radeon RX 6700 XT - 12 ГБ GDDR6, 192-bit, PCIe 4.0, HDMI, DisplayPort",
        "39 999 ₽",
        R.drawable.msi_rx_6700_xt,
        "Отличное решение для игр в разрешении 1440p с отличным балансом производительности и цены."
    ),
    GPU(
        productId = 13003,
        "Видеокарта ASUS ROG Strix GeForce RTX 4080 - 16 ГБ GDDR6X, 256-bit, PCIe 4.0, HDMI, DisplayPort",
        "119 999 ₽",
        R.drawable.asus_rog_strix_rtx_4080,
        "Высокопроизводительная видеокарта для киберспортивных и AAA-игр с великолепным охлаждением."
    ),
    GPU(
        productId = 13004,
        "Видеокарта Gigabyte AORUS ELITE GeForce RTX 4070 Ti - 12 ГБ GDDR6X, 192-bit, PCIe 4.0, HDMI, DisplayPort",
        "89 999 ₽",
        R.drawable.gigabyte_aorus_rtx_4070_ti,
        "Идеальная видеокарта для геймеров и контент-креаторов с поддержкой трассировки лучей."
    ),
    GPU(
        productId = 13005,
        "Видеокарта Sapphire Pulse Radeon RX 7900 XT - 20 ГБ GDDR6, 320-bit, PCIe 4.0, HDMI, DisplayPort",
        "89 499 ₽",
        R.drawable.sapphire_pulse_rx_7900_xt,
        "Мощное решение от AMD для высокопроизводительных задач и игр в 4K разрешении."
    ),
    GPU(
        productId = 13006,
        "Видеокарта ZOTAC Gaming GeForce RTX 3060 - 12 ГБ GDDR6, 192-bit, PCIe 4.0, HDMI, DisplayPort",
        "34 999 ₽",
        R.drawable.zotac_gaming_rtx_3060,
        "Оптимальный выбор для игр в 1080p и рабочих задач с умеренными требованиями."
    ),
    GPU(
        productId = 13007,
        "Видеокарта Palit GeForce GTX 1660 Super - 6 ГБ GDDR6, 192-bit, PCIe 3.0, HDMI, DisplayPort",
        "22 999 ₽",
        R.drawable.palit_gtx_1660_super,
        "Отличный вариант для бюджетных сборок с достойной производительностью."
    ),
    GPU(
        productId = 13008,
        "Видеокарта ASUS Dual Radeon RX 6500 XT - 4 ГБ GDDR6, 64-bit, PCIe 4.0, HDMI, DisplayPort",
        "14 999 ₽",
        R.drawable.asus_dual_rx_6500_xt,
        "Бюджетная видеокарта для повседневных задач и нетребовательных игр."
    ),
    GPU(
        productId = 13009,
        "Видеокарта MSI GeForce RTX 3060 Ti - 8 ГБ GDDR6, 256-bit, PCIe 4.0, HDMI, DisplayPort",
        "44 999 ₽",
        R.drawable.nvidia_rtx_3060_ti,
        "Мощная видеокарта для игр в 1440p и обработки контента."
    ),
    GPU(
        productId = 13010,
        "Видеокарта PowerColor Fighter Radeon RX 6600 - 8 ГБ GDDR6, 128-bit, PCIe 4.0, HDMI, DisplayPort",
        "19 999 ₽",
        R.drawable.powercolor_fighter_rx_6600,
        "Экономичный вариант для стабильной работы в 1080p с низким энергопотреблением."
    )
)