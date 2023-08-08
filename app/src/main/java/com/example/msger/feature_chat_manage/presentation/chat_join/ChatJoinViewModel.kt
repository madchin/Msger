package com.example.msger.feature_chat_manage.presentation.chat_join

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.core.presentation.navigation.NavigationRoute
import com.example.msger.feature_chat_manage.domain.use_case.JoinChatUseCase
import com.example.msger.feature_chat_manage.presentation.util.isChatIdValid
import com.example.msger.feature_chat_manage.presentation.util.isUsernameValid
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatJoinViewModel(
    private val joinChatUseCase: JoinChatUseCase
) : ViewModel() {

    var chatId: String by mutableStateOf("")
        private set

    var username: String by mutableStateOf("")
        private set

    var isChatIdValid: Boolean by mutableStateOf(true)
        private set

    var isUsernameValid: Boolean by mutableStateOf(true)
        private set

    var isLoading: Boolean by mutableStateOf(false)
        private set

    var responseError: String by mutableStateOf("")
        private set

    fun joinChat(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            isChatIdValid = isChatIdValid(chatId = chatId)
            isUsernameValid = isUsernameValid(username = username)
            if (!isChatIdValid || !isUsernameValid) {
                return@launch
            }
            isLoading = true

            try {
                joinChatUseCase(username = username, chatId = chatId)
                isLoading = false
                chatId = ""
                username = ""
                openAndPopUp(
                    NavigationRoute.Chat.withArgs(chatId),
                    NavigationRoute.JoinChat.route
                )
            } catch (e: Throwable) {
                isLoading = false
                responseError = e.message.toString()
                delay(5000L)
                responseError = ""
            }
        }
    }

    fun onChatIdInputChange(value: String) {
        chatId = value
        isChatIdValid = isChatIdValid(chatId = chatId)
    }

    fun onUsernameInputChange(value: String) {
        username = value
        isUsernameValid = isUsernameValid(username = username)
    }

    fun onChatIdValueClear() {
        chatId = ""
        isChatIdValid = true
    }

    fun onUsernameValueClear() {
        username = ""
        isUsernameValid = true
    }
}