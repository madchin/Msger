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
import com.example.msger.feature_onboarding.presentation.SplashScreenViewModel
import com.example.msger.feature_chat.presentation.chat.ChatViewModel
import com.example.msger.feature_chat_manage.presentation.chat_create.ChatCreateViewModel
import com.example.msger.feature_chat_manage.presentation.chat_list.ChatListViewModel
import com.example.msger.feature_chat.presentation.participant.ParticipantsViewModel
import com.example.msger.feature_chat_manage.presentation.chat_join.ChatJoinViewModel

val CreationExtras.application: MsgerApplication
    get() = checkNotNull(this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MsgerApplication)

object ViewModelFactoryProvider {
    val Factory = viewModelFactory {
        initializer {
            ResetPasswordViewModel(application.appContainer.resetPasswordUseCase)
        }
        initializer {
            SignInViewModel(
                application.appContainer.getEmailFromDeepLinkUseCase,
                application.appContainer.signInUseCase
            )
        }
        initializer {
            SignUpViewModel(application.appContainer.signUpUseCase)
        }
        initializer {
            SplashScreenViewModel(application.appContainer.isUserSignedInUseCase)
        }
        initializer {
            ChatListViewModel(
                signOutUseCase = application.appContainer.signOutUseCase,
                getChatsUseCase = application.appContainer.getChatsUseCase,
                joinChatFromChatListUseCase = application.appContainer.joinChatFromChatListUseCase
            )
        }
        initializer {
            ChatCreateViewModel(application.appContainer.createChatUseCase)
        }
        initializer {
            ChatJoinViewModel(application.appContainer.joinChatUseCase)
        }
        initializer {
            ChatViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                getChatMessagesUseCase = application.appContainer.getChatMessagesUseCase,
                sendMessageUseCase = application.appContainer.sendMessageUseCase
            )
        }
        initializer {
            ParticipantsViewModel(
                this.createSavedStateHandle(),
                application.appContainer.getChatMembersUseCase
            )
        }
    }
}