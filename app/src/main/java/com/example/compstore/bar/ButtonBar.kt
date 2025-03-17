package com.example.compstore.bar

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ButtonBar(navController: NavController) {
    NavigationBar(modifier = Modifier.height(56.dp)) {
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.Black,
                    modifier = Modifier.size(48.dp)
                )
            },
            selected = false,
            onClick = { navController.navigate("home") }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "Basket",
                    tint = Color.Black,
                    modifier = Modifier.size(48.dp)
                )
            },
            selected = false,
            onClick = { navController.navigate("basket") }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Email,
                    contentDescription = "Chat",
                    tint = Color.Black,
                    modifier = Modifier.size(48.dp)
                )
            },
            selected = false,
            onClick = { navController.navigate("chat") }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.Black,
                    modifier = Modifier.size(48.dp)
                )
            },
            selected = false,
            onClick = { navController.navigate("settings") }
        )
    }
}
