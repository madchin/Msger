package com.example.msger.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.msger.MsgerApplication
import com.example.msger.ui.screens.ForgotPasswordViewModel
import com.example.msger.ui.screens.SignInViewModel
import com.example.msger.ui.screens.SignUpViewModel
import com.example.msger.ui.screens.SplashScreenViewModel
import com.example.msger.ui.screens.authorized.HomeViewModel

object ViewModelFactoryProvider {
    val Factory = viewModelFactory {
        initializer {
            val application = checkNotNull(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MsgerApplication)
            ForgotPasswordViewModel(application.appContainer.accountService)
        }
        initializer {
            val application = checkNotNull(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as MsgerApplication
            SignInViewModel(application.appContainer.accountService)
        }
        initializer {
            val application = checkNotNull(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as MsgerApplication
            SignUpViewModel(application.appContainer.accountService)
        }
        initializer {
            val application = checkNotNull(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MsgerApplication)
            SplashScreenViewModel(application.appContainer.accountService)
        }
        initializer {
            val application =
                checkNotNull(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as MsgerApplication
            HomeViewModel(application.appContainer.accountService)
        }
    }
}