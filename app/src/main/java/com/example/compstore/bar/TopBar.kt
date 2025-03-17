package com.example.compstore.bar

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.compstore.ui.components.MenuItemProduct
import com.example.compstore.ui.components.ProductDetailsDialog
import com.example.compstore.ui.screen.PCComponentsScreens.Product
import com.example.compstore.viewmodel.BasketViewModel
import com.example.compstore.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    userViewModel: UserViewModel,
    allProducts: List<Product>,
    basketViewModel: BasketViewModel
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    // Определяем текущий маршрут
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column {
        TopAppBar(
            title = {},
            modifier = Modifier.height(56.dp),
            navigationIcon = {
                IconButton(
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            val currentUser = userViewModel.loggedInUser.first()
                            val currentRoute = navController.currentBackStackEntry?.destination?.route
                            if (currentUser != null && currentRoute != "profile") {
                                navController.navigate("profile")
                            } else if (currentUser == null && currentRoute != "login") {
                                navController.navigate("login")
                            }
                        }
                    }
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Профиль",
                        tint = Color.Black,
                        modifier = Modifier.size(56.dp)
                    )
                }
            }
        )

        // Поле поиска отображается только на HomeScreen
        if (currentRoute == "home") {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Поиск товаров...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Поиск",
                        tint = Color.Gray
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }

        // Список результатов поиска тоже отображается только на HomeScreen
        if (searchQuery.isNotEmpty() && currentRoute == "home") {
            val filteredProducts = allProducts.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }

            LazyColumn {
                items(items = filteredProducts) { product ->
                    MenuItemProduct(
                        name = product.name,
                        price = product.price,
                        imageResId = product.imageResId,
                        onClick = { selectedProduct = product },
                        onAddToCartClick = { basketViewModel.addToCart(product.productId) }
                    )
                }
            }
        }
    }

    selectedProduct?.let { product ->
        ProductDetailsDialog(
            product = product,
            onDismiss = { selectedProduct = null },
            onAddToCart = { basketViewModel.addToCart(it.productId) }
        )
    }
}