package com.example.compstore.nav

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compstore.screen.AddressSelectionScreen
import com.example.compstore.screen.HomeScreen
import com.example.compstore.screen.WelcomeScreen

@Composable
fun MainNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        composable(
            route = "welcome",
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() }
        ) {
            WelcomeScreen(
                onNavigateToAddressSelection = {
                    navController.navigate("addressSelection")
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
                    navController.navigate("home")
                },
                onSkip = {
                    navController.navigate("home")
                }
            )
        }
        composable(
            route = "home",
            enterTransition = { slideInHorizontally { -it } },
            exitTransition = { slideOutHorizontally { it } },
        ) {
            HomeScreen()
        }
    }
}