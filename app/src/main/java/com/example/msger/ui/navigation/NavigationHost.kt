package com.example.msger.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.msger.common.extensions.openAndPopUp
import com.example.msger.ui.screens.ForgotPasswordScreen
import com.example.msger.ui.screens.SignInScreen
import com.example.msger.ui.screens.SignUpScreen
import com.example.msger.ui.screens.SplashScreen
import com.example.msger.ui.screens.authorized.HomeScreen


@Composable
fun NavigationHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = SPLASH_SCREEN) {
        composable(SPLASH_SCREEN) {
            SplashScreen(openAndPopUp = navController::openAndPopUp)
        }
        composable(SIGN_UP) {
            SignUpScreen(
                openAndPopUp = navController::openAndPopUp,
                navigateToSignIn = { navController.navigate(SIGN_IN) }
            )
        }
        composable(SIGN_IN) {
            SignInScreen(
                openAndPopUp = navController::openAndPopUp,
                navigateToSignUp = { navController.navigate(SIGN_UP) },
                navigateToForgottenPassword = { navController.navigate(FORGOT_PASSWORD) }
            )
        }
        composable(FORGOT_PASSWORD) {
            ForgotPasswordScreen(
                navigateToSignIn = { navController.navigate(SIGN_IN) }
            )
        }
        composable(HOME) {
            HomeScreen(openAndPopUp = navController::openAndPopUp)
        }
    }
}