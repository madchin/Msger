package com.example.msger.feature_chat_manage.presentation.chat_create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.core.presentation.navigation.NavigationRoute
import com.example.msger.feature_chat_manage.domain.model.Chat
import com.example.msger.feature_chat_manage.domain.use_case.CreateChatUseCase
import com.example.msger.feature_chat_manage.presentation.util.isChatNameValid
import com.example.msger.feature_chat_manage.presentation.util.isUsernameValid
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CreateChatViewModel(
    private val createChatUseCase: CreateChatUseCase
) : ViewModel() {

    var chatName: String by mutableStateOf("")
        private set

    var username: String by mutableStateOf("")
        private set

    var isChatNameValid: Boolean by mutableStateOf(true)
        private set

    var isUsernameValid: Boolean by mutableStateOf(true)
        private set

    var isLoading: Boolean by mutableStateOf(false)
        private set

    var responseError: String by mutableStateOf("")
        private set

    fun createChat(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            isChatNameValid = isChatNameValid(chatName = chatName)
            isUsernameValid = isUsernameValid(username = username)
            if (!isChatNameValid || !isUsernameValid) {
                return@launch
            }
            isLoading = true

            try {
                val chatId: String =
                    createChatUseCase(username = username, chat = Chat(name = chatName))
                isLoading = false
                chatName = ""
                username = ""
                openAndPopUp(
                    NavigationRoute.Chat.withArgs(chatId),
                    NavigationRoute.CreateChat.route
                )
            } catch (e: Throwable) {
                isLoading = false
                responseError = e.message.toString()
                delay(5000L)
                responseError = ""
            }
        }
    }

    fun onChatNameInputChange(value: String) {
        chatName = value
        isChatNameValid = isChatNameValid(chatName = chatName)
    }

    fun onUsernameInputChange(value: String) {
        username = value
        isUsernameValid = isUsernameValid(username = username)
    }

    fun onChatNameValueClear() {
        chatName = ""
        isChatNameValid = true
    }

    fun onUsernameValueClear() {
        username = ""
        isUsernameValid = true
    }

}