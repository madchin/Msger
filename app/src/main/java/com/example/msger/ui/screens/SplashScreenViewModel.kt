package com.example.msger.ui.screens

import androidx.lifecycle.ViewModel
import com.example.msger.data.services.AccountService
import com.example.msger.ui.HOME
import com.example.msger.ui.SIGN_IN
import com.example.msger.ui.SPLASH_SCREEN

class SplashScreenViewModel(private val accountService: AccountService) : ViewModel() {

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        if (accountService.isSignedIn) {
            openAndPopUp(HOME, SPLASH_SCREEN)
            return
        }
        openAndPopUp(SIGN_IN, SPLASH_SCREEN)
    }
}