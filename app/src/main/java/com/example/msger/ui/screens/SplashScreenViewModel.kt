package com.example.msger.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.msger.MsgerApplication
import com.example.msger.data.services.AuthService
import com.example.msger.ui.navigation.HOME
import com.example.msger.ui.navigation.SIGN_IN
import com.example.msger.ui.navigation.SPLASH_SCREEN

class SplashScreenViewModel(private val authService: AuthService) : ViewModel() {

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        if (authService.isSignedIn) {
            openAndPopUp(HOME, SPLASH_SCREEN)
            return
        }
        openAndPopUp(SIGN_IN, SPLASH_SCREEN)
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY] as MsgerApplication)
                SplashScreenViewModel(application.appContainer.authService)
            }
        }
    }

}