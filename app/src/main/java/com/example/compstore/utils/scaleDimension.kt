package com.example.compstore.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp

@Composable
fun scaleDimension(dimension: Dp, baseWidth: Int = 360): Dp {
    // Получаем текущую конфигурацию устройства
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    // Вычисляем коэффициент масштабирования
    val scaleFactor = screenWidthDp / baseWidth.toFloat()
    return dimension * scaleFactor
}