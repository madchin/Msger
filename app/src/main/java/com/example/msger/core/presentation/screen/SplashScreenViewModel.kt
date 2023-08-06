package com.example.msger.core.presentation.screen

import androidx.lifecycle.ViewModel
import com.example.msger.feature_authentication.domain.repository.AuthRepository
import com.example.msger.core.presentation.navigation.NavigationRoute

class SplashScreenViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        if (authRepository.isSignedIn) {
            openAndPopUp(NavigationRoute.ChatList.route, NavigationRoute.SplashScreen.route)
            return
        }
        openAndPopUp(NavigationRoute.SignIn.route, NavigationRoute.SplashScreen.route)
    }
}