package com.example.msger.feature_authentication.presentation.util

sealed class NavigationAuthentication(val route: String) {

    object SignIn : NavigationAuthentication(route = "sign_in")

    object SignUp : NavigationAuthentication(route = "sign_up")

    object ResetPassword : NavigationAuthentication(route = "reset-password")

}