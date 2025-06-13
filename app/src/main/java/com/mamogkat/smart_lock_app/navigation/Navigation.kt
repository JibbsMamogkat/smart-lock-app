package com.mamogkat.smart_lock_app.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mamogkat.smart_lock_app.ui.screens.LoginScreen
import com.mamogkat.smart_lock_app.ui.screens.RegisterScreen


sealed class Screen(val route: String) {
    object Login: Screen ("login")
    object Register: Screen("register")
    object Home: Screen("home")

}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) { LoginScreen(navController)}
        composable(Screen.Register.route) { RegisterScreen(navController)}
    }
}