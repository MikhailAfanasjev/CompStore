package com.example.compstore.nav

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compstore.bar.ButtonBar
import com.example.compstore.bar.TopBar
import com.example.compstore.ui.screen.BasketScreen
import com.example.compstore.ui.screen.ChatScreen
import com.example.compstore.ui.screen.HomeScreen
import com.example.compstore.ui.screen.PCComponentsScreens.CasesScreen
import com.example.compstore.ui.screen.PCComponentsScreens.CoolersScreen
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.cases
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.coolers
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.gpus
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.hdds
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.monitors
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.motherboards
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.processors
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.psus
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.ramModules
import com.example.compstore.ui.screen.PCComponentsScreens.DataClasses.ssds
import com.example.compstore.ui.screen.PCComponentsScreens.HDDScreen
import com.example.compstore.ui.screen.PCComponentsScreens.MonitorsScreen
import com.example.compstore.ui.screen.PCComponentsScreens.MotherboardsScreen
import com.example.compstore.ui.screen.PCComponentsScreens.PSUScreen
import com.example.compstore.ui.screen.PCComponentsScreens.ProcessorsScreen
import com.example.compstore.ui.screen.PCComponentsScreens.RAMScreen
import com.example.compstore.ui.screen.PCComponentsScreens.SSDScreen
import com.example.compstore.ui.screen.PCComponentsScreens.VideocardsScreen
import com.example.compstore.ui.screen.SettingsScreen
import com.example.compstore.ui.screen.profile.EditAddressScreen
import com.example.compstore.ui.screen.profile.EditPasswordScreen
import com.example.compstore.ui.screen.profile.EditPaymentMethodScreen
import com.example.compstore.ui.screen.profile.EditUserScreen
import com.example.compstore.ui.screen.profile.HistoryScreen
import com.example.compstore.ui.screen.profile.LoginScreen
import com.example.compstore.ui.screen.profile.ProfileScreen
import com.example.compstore.ui.screen.profile.RegistrationScreen
import com.example.compstore.viewmodel.AddressViewModel
import com.example.compstore.viewmodel.BasketViewModel
import com.example.compstore.viewmodel.ChatViewModel
import com.example.compstore.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val addressViewModel: AddressViewModel = hiltViewModel()
    val userViewModel: UserViewModel = hiltViewModel()
    val chatViewModel: ChatViewModel = hiltViewModel()

    val loggedInUser by userViewModel.loggedInUser.collectAsState(initial = null)

    val activity = LocalContext.current as ComponentActivity
    val basketViewModel: BasketViewModel = hiltViewModel(activity)

    // Определяем список товаров, объединяя нужные коллекции
    val allProducts = processors + gpus + psus + motherboards + coolers + ramModules + cases + hdds + monitors + ssds

    val screensWithButtonBar = listOf(
        "home",
        "basket",
        "chat",
        "settings",
        "processorsScreen",
        "motherboardsScreen",
        "videocardsScreen",
        "ramScreen",
        "powersuppliesScreen",
        "computercoolingScreen"
    )

    val screensWithoutTopAppBar = listOf("welcome")

    // Проверка данных пользователя при запуске приложения
    LaunchedEffect(loggedInUser) {
        if (loggedInUser != null) {
            Log.d("NavGraph", "User is logged in, navigating to home")
            navController.navigate("home") {
                popUpTo("home") { inclusive = true }
            }
        } else {
            Log.d("NavGraph", "No logged in user, navigating to home")
            navController.navigate("home") {
                popUpTo("home") { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            val currentDestination by navController.currentBackStackEntryAsState()
            val currentRoute = currentDestination?.destination?.route
            if (currentRoute !in screensWithoutTopAppBar) {
                TopBar(
                    navController = navController,
                    userViewModel = userViewModel,
                    allProducts = allProducts,           // передаем список товаров
                    basketViewModel = basketViewModel      // передаем ViewModel корзины
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
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(navController)
            }
            composable("basket") {
                BasketScreen(
                    basketViewModel = basketViewModel,
                    onProductClick = {},
                    onRemoveFromCart = {},
                    onBuyClick = { navController.navigate("login") }
                )
            }
            composable("chat") {
                ChatScreen(chatViewModel = chatViewModel)
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
                backStackEntry.arguments?.getString("phone")
                LoginScreen(navController = navController)
            }
            composable("profile") {
                ProfileScreen(navController = navController)
            }
            composable("registration") {
                RegistrationScreen(navController)
            }
            composable("historyScreen") {
                HistoryScreen(
                    allProducts = processors + gpus + psus + motherboards + coolers + ramModules + cases + hdds + monitors + ssds
                )
            }
            composable("edit") {
                EditUserScreen(navController)
            }
            composable("editAddress") {
                EditAddressScreen(navController)
            }
            composable("editPaymentMethod") {
                EditPaymentMethodScreen(navController)
            }
            composable("editPassword") {
                EditPasswordScreen(navController)
            }
            composable("processorsScreen") { ProcessorsScreen(basketViewModel = basketViewModel) }
            composable("motherboardsScreen") { MotherboardsScreen(basketViewModel = basketViewModel) }
            composable("videocardsScreen") { VideocardsScreen(basketViewModel = basketViewModel) }
            composable("ramScreen") { RAMScreen(basketViewModel = basketViewModel) }
            composable("powersuppliesScreen") { PSUScreen(basketViewModel = basketViewModel) }
            composable("computercoolingScreen") { CoolersScreen(basketViewModel = basketViewModel) }
            composable("casesScreen") { CasesScreen(basketViewModel = basketViewModel) }
            composable("HDDScreen") { HDDScreen(basketViewModel = basketViewModel) }
            composable("SSDScreen") { SSDScreen(basketViewModel = basketViewModel) }
            composable("monitorsScreen") { MonitorsScreen(basketViewModel = basketViewModel) }
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