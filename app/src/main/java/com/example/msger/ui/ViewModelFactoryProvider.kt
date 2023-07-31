package com.example.msger.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.msger.MsgerApplication
import com.example.msger.ui.screens.ForgotPasswordViewModel
import com.example.msger.ui.screens.SignInViewModel
import com.example.msger.ui.screens.SignUpViewModel
import com.example.msger.ui.screens.SplashScreenViewModel
import com.example.msger.ui.screens.authorized.HomeViewModel

val CreationExtras.application: MsgerApplication
    get() = checkNotNull(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MsgerApplication)

object ViewModelFactoryProvider {
    val Factory = viewModelFactory {
        initializer {
            ForgotPasswordViewModel(application.appContainer.authService)
        }
        initializer {
            SignInViewModel(application.appContainer.authService)
        }
        initializer {
            SignUpViewModel(application.appContainer.authService)
        }
        initializer {
            SplashScreenViewModel(application.appContainer.authService)
        }
        initializer {
            HomeViewModel(application.appContainer.authService, application.appContainer.dbService)
        }
    }
}