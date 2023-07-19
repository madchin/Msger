package com.example.msger.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.msger.ui.screens.HomeScreen
import com.example.msger.ui.screens.SignInScreen
import com.example.msger.ui.screens.SignUpScreen
import com.example.msger.ui.screens.SplashScreen

@Composable
fun NavigationHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = SPLASH_SCREEN) {
        composable(SPLASH_SCREEN) {
            SplashScreen()
        }
        composable(SIGN_UP) {
            SignUpScreen()
        }
        composable(SIGN_IN) {
            SignInScreen()
        }
        composable(HOME) {
            HomeScreen()
        }
    }
}