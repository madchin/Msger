package com.example.msger.ui

sealed class NavigationRoute(val route: String) {
    object Home : NavigationRoute(route = "home")
    object SignUp : NavigationRoute(route = "sign-up")
    object SignIn : NavigationRoute(route = "sign-in")
    object SplashScreen : NavigationRoute(route = "splash-screen")
    object ForgotPassword : NavigationRoute(route = "forgot-password")
}