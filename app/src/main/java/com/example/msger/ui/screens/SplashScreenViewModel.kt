package com.example.msger.ui.screens

import androidx.lifecycle.ViewModel
import com.example.msger.data.services.auth.AuthService
import com.example.msger.ui.NavigationRoute

class SplashScreenViewModel(private val authService: AuthService) : ViewModel() {

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        if (authService.isSignedIn) {
            openAndPopUp(NavigationRoute.Home.route, NavigationRoute.SplashScreen.route)
            return
        }
        openAndPopUp(NavigationRoute.SignIn.route, NavigationRoute.SplashScreen.route)
    }
}