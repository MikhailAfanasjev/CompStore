package com.example.compstore.nav

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compstore.bar.ButtonBar
import com.example.compstore.screen.AddressSelectionScreen
import com.example.compstore.screen.BasketScreen
import com.example.compstore.screen.ChatScreen
import com.example.compstore.screen.DetailsScreen
import com.example.compstore.screen.HomeScreen
import com.example.compstore.screen.ProfileScreen
import com.example.compstore.screen.SettingsScreen
import com.example.compstore.screen.WelcomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val screensWithButtonBar = listOf("home", "basket", "chat", "settings", "profile")
    val screensWithoutTopAppBar = listOf("welcome", "addressSelection", "basket")

    Scaffold(
        topBar = {
            val currentDestination by navController.currentBackStackEntryAsState()
            val currentRoute = currentDestination?.destination?.route

            if (currentRoute !in screensWithoutTopAppBar) {
                TopAppBar(
                    title = { Text("Profile") },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate("profile") }) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Профиль",
                                tint = Color.Black,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            val currentDestination by navController.currentBackStackEntryAsState()
            val currentRoute = currentDestination?.destination?.route
            if (currentRoute in screensWithButtonBar) {
                ButtonBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "welcome",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(
                route = "welcome",
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() }
            ) {
                WelcomeScreen(
                    onNavigateToAddressSelection = {
                        navController.navigate("addressSelection")
                    },
                    onNavigateToHome = {
                        navController.navigate("home") {
                            popUpTo("welcome") { inclusive = true }
                        }
                    }
                )
            }
            composable(
                route = "addressSelection",
                enterTransition = { slideInHorizontally { it } },
                exitTransition = { slideOutHorizontally { -it } },
            ) {
                AddressSelectionScreen(
                    onSave = {
                        navController.navigate("home") {
                            popUpTo("addressSelection") { inclusive = true }
                        }
                    },
                    onSkip = {
                        navController.navigate("home") {
                            popUpTo("addressSelection") { inclusive = true }
                        }
                    }
                )
            }
            composable("home") { HomeScreen(navController) }
            composable("details/{description}") { backStackEntry ->
                val description = backStackEntry.arguments?.getString("description") ?: "No description"
                DetailsScreen(description)
            }
            composable(
                route = "basket",
                enterTransition = { slideInHorizontally { it } },
                exitTransition = { slideOutHorizontally { -it } },
            ) {
                BasketScreen()
            }
            composable(
                route = "chat",
                enterTransition = { slideInHorizontally { it } },
                exitTransition = { slideOutHorizontally { -it } },
            ) {
                ChatScreen()
            }
            composable(
                route = "settings",
                enterTransition = { slideInHorizontally { it } },
                exitTransition = { slideOutHorizontally { -it } },
            ) {
                SettingsScreen()
            }
            composable(
                route = "profile",
                enterTransition = { slideInHorizontally { it } },
                exitTransition = { slideOutHorizontally { -it } },
            ) {
                ProfileScreen(
                    onSave = {
                        navController.navigate("home") // Navigate to HomeScreen
                    }
                )
            }
        }
    }
}