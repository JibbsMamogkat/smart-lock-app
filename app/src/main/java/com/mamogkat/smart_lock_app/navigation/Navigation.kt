package com.mamogkat.smart_lock_app.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mamogkat.smart_lock_app.ui.screens.*
import com.mamogkat.smart_lock_app.viewmodel.AuthViewModel
import com.mamogkat.smart_lock_app.viewmodel.LockViewModel


sealed class Screen(val route: String) {
    object Login: Screen ("login")
    object Register: Screen("register")
    object Dashboard: Screen("dashboard")
    object RegisterUser: Screen("register_user")

}

@Composable
fun AppNavHost(navController: NavHostController, lockViewModel: LockViewModel, authViewModel: AuthViewModel) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) { LoginScreen(navController)}
        composable(Screen.Register.route) { RegisterScreen(navController, authViewModel)}
        composable(Screen.Dashboard.route) { DashboardScreen(navController, lockViewModel)}
        composable(Screen.RegisterUser.route) { RegisterUserScreen(navController, authViewModel) }
    }
}