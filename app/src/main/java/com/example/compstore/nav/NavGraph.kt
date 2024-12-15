package com.example.compstore.nav

import android.util.Log
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compstore.bar.ButtonBar
import com.example.compstore.screen.BasketScreen
import com.example.compstore.screen.ChatScreen
import com.example.compstore.screen.HomeScreen
import com.example.compstore.screen.SettingsScreen
import com.example.compstore.screen.WelcomeScreen
import com.example.compstore.screen.profile.EditAddressScreen
import com.example.compstore.screen.profile.EditScreen
import com.example.compstore.screen.profile.LoginScreen
import com.example.compstore.screen.profile.ProfileScreen
import com.example.compstore.screen.profile.RegistrationScreen
import com.example.compstore.viewmodel.StoreViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val screensWithButtonBar = listOf("home", "basket", "chat", "settings", "profile")
    val screensWithoutTopAppBar = listOf("welcome", "basket")
    val storeViewModel: StoreViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            val currentDestination by navController.currentBackStackEntryAsState()
            val currentRoute = currentDestination?.destination?.route

            if (currentRoute !in screensWithoutTopAppBar) {
                TopAppBar(
                    title = { Text("Profile") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                CoroutineScope(Dispatchers.Main).launch {
                                    val hasUser = storeViewModel.hasUserData()
                                    val currentRoute = navController.currentBackStackEntry?.destination?.route

                                    Log.d("NavGraph", "Navigating from $currentRoute")
                                    Log.d("NavGraph", "Has user data: $hasUser")

                                    if (hasUser && currentRoute != "profile") {
                                        Log.d("NavGraph", "Navigating to profile")
                                        navController.navigate("profile")
                                    } else if (!hasUser && currentRoute != "login") {
                                        Log.d("NavGraph", "Navigating to login")
                                        navController.navigate("login")
                                    }
                                }
                            }
                        ) {
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

            Log.d("NavGraph", "Current route for bottom bar: $currentRoute")

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
            composable("welcome") {
                WelcomeScreen(
                    onNavigateToHome = {
                        navController.navigate("home") {
                            popUpTo("welcome") { inclusive = true }
                        }
                    }
                )
            }
            composable("home") {
                HomeScreen(navController)
            }
            composable("basket") {
                BasketScreen()
            }
            composable("chat") {
                ChatScreen()
            }
            composable("settings") {
                SettingsScreen()
            }
            composable("login") {
                LoginScreen(navController = navController)
            }
            composable(
                route = "login/{phone}",
                arguments = listOf(navArgument("phone") { type = NavType.StringType })
            ) { backStackEntry ->
                val phone = backStackEntry.arguments?.getString("phone")
                LoginScreen(navController = navController)
            }
            composable("profile") {
                ProfileScreen(navController)
            }
            composable("registration") {
                RegistrationScreen(navController)
            }
            composable("edit") {
                EditScreen(navController)
            }
            composable("editAddress") {
                EditAddressScreen()
            }
            // composable("editHistory") { EditHistoryScreen() }
            // composable("editPaymentMethod") { EditPaymentMethodScreen() }
            // composable("editPassword") { EditPasswordScreen() }
        }
    }
}


//composable(
//route = "editPassword",
//enterTransition = { slideInHorizontally { it } },
//exitTransition = { slideOutHorizontally { -it } }
//) {
//    EditAddressScreen()
//}