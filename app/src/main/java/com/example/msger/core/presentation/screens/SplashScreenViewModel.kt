package com.example.msger.core.presentation.screens

import androidx.lifecycle.ViewModel
import com.example.msger.feature_authentication.domain.repository.AuthRepository
import com.example.msger.core.presentation.navigation.NavigationRoute
import com.example.msger.feature_authentication.presentation.util.NavigationAuthentication
import com.example.msger.feature_chat_manage.presentation.util.NavigationChatManage

class SplashScreenViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        if (authRepository.isSignedIn) {
            openAndPopUp(NavigationChatManage.ChatList.route, NavigationRoute.SplashScreen.route)
            return
        }
        openAndPopUp(NavigationAuthentication.SignIn.route, NavigationRoute.SplashScreen.route)
    }
}