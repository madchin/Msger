package com.example.msger.core.presentation.util

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.msger.core.data.MsgerApplication
import com.example.msger.feature_authentication.presentation.reset_password.ResetPasswordViewModel
import com.example.msger.feature_authentication.presentation.sign_in.SignInViewModel
import com.example.msger.feature_authentication.presentation.sign_up.SignUpViewModel
import com.example.msger.core.presentation.screens.SplashScreenViewModel
import com.example.msger.core.presentation.screens.authorized.ChatViewModel
import com.example.msger.core.presentation.screens.authorized.CreateChatViewModel
import com.example.msger.core.presentation.screens.authorized.HomeViewModel
import com.example.msger.core.presentation.screens.authorized.ParticipantsViewModel

val CreationExtras.application: MsgerApplication
    get() = checkNotNull(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MsgerApplication)

object ViewModelFactoryProvider {
    val Factory = viewModelFactory {
        initializer {
            ResetPasswordViewModel(application.appContainer.resetPasswordUseCase)
        }
        initializer {
            SignInViewModel(
                application.appContainer.getEmailFromRecoverPasswordRedirectionUseCase,
                application.appContainer.signInUseCase
            )
        }
        initializer {
            SignUpViewModel(application.appContainer.signUpUseCase)
        }
        initializer {
            SplashScreenViewModel(application.appContainer.authRepository)
        }
        initializer {
            HomeViewModel(application.appContainer.authRepository, application.appContainer.dbService)
        }
        initializer {
            CreateChatViewModel(application.appContainer.dbService)
        }
        initializer {
            ChatViewModel(this.createSavedStateHandle(), application.appContainer.dbService)
        }
        initializer {
            ParticipantsViewModel(this.createSavedStateHandle(), application.appContainer.dbService)
        }
    }
}