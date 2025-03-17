package com.example.compstore.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.compstore.ui.components.CustomButton
import com.example.compstore.ui.components.MenuItemCategory
import com.example.compstore.ui.theme.BackgroundLightGrey
import com.example.compstore.utils.scaleDimension
import com.example.linguareader.R

@Composable
fun HomeScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BackgroundLightGrey
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(scaleDimension(1.dp))
            ) {

                CustomButton(
                    text = "История заказов",
                    onClick = {
                        navController.navigate("historyScreen")
                    },
                    modifier = Modifier
                        .padding(vertical = scaleDimension(1.dp))
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = scaleDimension(1.dp)),
                    horizontalArrangement = Arrangement.spacedBy(scaleDimension(8.dp)),
                    verticalArrangement = Arrangement.spacedBy(scaleDimension(8.dp))
                ) {
                    val categoriesWithImages = listOf(
                        Pair("ProcessorsScreen" to "Процессоры", R.drawable.ic_processors),
                        Pair("MotherboardsScreen" to "Материнские платы", R.drawable.ic_matherboards),
                        Pair("VideocardsScreen" to "Видеокарты", R.drawable.ic_videocards),
                        Pair("RAMScreen" to "Оперативная память", R.drawable.ic_ram),
                        Pair("PowersuppliesScreen" to "Блоки питания", R.drawable.ic_powersupplies),
                        Pair("ComputercoolingScreen" to "Охлаждение для ПК", R.drawable.ic_computercooling),
                        Pair("CasesScreen" to "Корпуса", R.drawable.ic_cases),
                        Pair("SSDScreen" to "SSD", R.drawable.ic_ssd),
                        Pair("HDDScreen" to "HDD", R.drawable.ic_hdd),
                        Pair("MonitorsScreen" to "Мониторы", R.drawable.ic_monitors)
                    )

                    items(categoriesWithImages) { (routeWithName, imageResId) ->
                        val (route, name) = routeWithName
                        MenuItemCategory(
                            name = name,
                            onClick = {
                                navController.navigate(route)
                            },
                            modifier = Modifier
                                .aspectRatio(1f)
                                .padding(scaleDimension(1.dp)),
                            imageResId = imageResId
                        )
                    }
                }
            }
        }
    }
}