package com.cs4520.assignment5

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cs4520.assignment5.Fragments.LoginScreen
import com.cs4520.assignment5.Fragments.ProductListScreen

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("productList") }
            )}
        composable("productList") { ProductListScreen() }
    }
}


