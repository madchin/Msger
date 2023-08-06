package com.example.msger.feature_onboarding.presentation

import androidx.lifecycle.ViewModel
import com.example.msger.core.presentation.navigation.NavigationRoute
import com.example.msger.feature_onboarding.domain.use_case.IsUserSignedInUseCase

class SplashScreenViewModel(
    private val isUserSignedInUseCase: IsUserSignedInUseCase
    ) : ViewModel() {

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        if (isUserSignedInUseCase()) {
            openAndPopUp(NavigationRoute.ChatList.route, NavigationRoute.SplashScreen.route)
            return
        }
        openAndPopUp(NavigationRoute.SignIn.route, NavigationRoute.SplashScreen.route)
    }
}