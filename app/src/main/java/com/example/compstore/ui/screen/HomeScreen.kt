package com.example.compstore.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.compstore.ui.components.CustomButton
import com.example.compstore.ui.components.MenuItemCategory
import com.example.linguareader.R

@Composable
fun HomeScreen(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.LightGray
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val categories = listOf(
                        "Процессоры",
                        "Материнские платы",
                        "Видеокарты",
                        "Оперативная память",
                        "Блоки питания",
                        "Охлаждение для ПК"
                    )

                    items(categories) { category ->
                        Box(
                            modifier = Modifier
                                .width(120.dp)
                                .aspectRatio(1f)
                                .clickable {
                                    navController.navigate("${category}Screen")
                                }
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = category,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 14.sp
                                ),
                                maxLines = 2,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }

                CustomButton(
                    text = "Статус заказа",
                    onClick = {
                        navController.navigate("orderStatus")
                    },
                    modifier = Modifier
                        .padding(vertical = 1.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val categoriesWithImages = listOf(
                        Pair("ProcessorsScreen" to "Процессоры", R.drawable.ic_processors),
                        Pair("MotherboardsScreen" to "Материнские платы", R.drawable.ic_matherboards),
                        Pair("VideocardsScreen" to "Видеокарты", R.drawable.ic_videocards),
                        Pair("RAMScreen" to "Оперативная память", R.drawable.ic_ram),
                        Pair("PowersuppliesScreen" to "Блоки питания", R.drawable.ic_powersupplies),
                        Pair("ComputercoolingScreen" to "Охлаждение для ПК", R.drawable.ic_computercooling)
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
                                .padding(4.dp), // Добавление отступа для текста
                            imageResId = imageResId
                        )
                    }
                }
            }
        }
    }
}