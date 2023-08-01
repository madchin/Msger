package com.example.msger.ui.screens.authorized

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.R
import com.example.msger.common.extensions.chatNameErrorText
import com.example.msger.common.extensions.isChatNameValid
import com.example.msger.common.extensions.isUsernameValid
import com.example.msger.common.extensions.usernameErrorText
import com.example.msger.data.model.Chat
import com.example.msger.data.services.db.DbService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class CreateChatUiState(
    val chatName: String = "",
    val username: String = "",
    val isChatNameValid: Boolean = true,
    val isUsernameValid: Boolean = true,
    val chatNameInputErrorText: Int = R.string.input_required,
    val usernameInputErrorText: Int = R.string.input_required,
    val isLoading: Boolean = false,
    val responseError: String = ""
)

class CreateChatViewModel(private val dbService: DbService) : ViewModel() {

    var uiState: CreateChatUiState by mutableStateOf(CreateChatUiState())
        private set

    private val chatName: String
        get() = uiState.chatName

    private val username: String
        get() = uiState.username
    private val chatNameInputErrorText: Int
        get() = chatName.chatNameErrorText()
    private val usernameInputErrorText: Int
        get() = username.usernameErrorText()
    private val isChatNameValid: Boolean
        get() = chatName.isChatNameValid()

    private val isUsernameValid: Boolean
        get() = username.isUsernameValid()

    fun createChat() {
        viewModelScope.launch {
            uiState = uiState.copy(
                isChatNameValid = isChatNameValid,
                isUsernameValid = isUsernameValid,
                chatNameInputErrorText = chatNameInputErrorText,
                usernameInputErrorText = usernameInputErrorText,
            )

            if (!isChatNameValid || !isUsernameValid) {
                return@launch
            }
            uiState = uiState.copy(isLoading = true)

            try {
                dbService.createChat(username, Chat(name = chatName))
                uiState = uiState.copy(isLoading = false)
            } catch (e: Throwable) {
                uiState = uiState.copy(isLoading = false, responseError = e.message.toString())
                delay(5000L)
                uiState = uiState.copy(responseError = "")
            }
        }
    }

    fun onChatNameInputChange(value: String) {
        uiState = uiState.copy(chatName = value)
        uiState = uiState.copy(
            isChatNameValid = isChatNameValid,
            chatNameInputErrorText = chatNameInputErrorText
        )
    }

    fun onUsernameInputChange(value: String) {
        uiState = uiState.copy(username = value)
        uiState = uiState.copy(
            isUsernameValid = isUsernameValid,
            usernameInputErrorText = usernameInputErrorText
        )
    }

    fun onChatNameValueClear() {
        uiState = uiState.copy(chatName = "")
        uiState = uiState.copy(
            isChatNameValid = isChatNameValid,
            chatNameInputErrorText = chatNameInputErrorText
        )
    }

    fun onUsernameValueClear() {
        uiState = uiState.copy(username = "")
        uiState = uiState.copy(
            isUsernameValid = isUsernameValid,
            usernameInputErrorText = usernameInputErrorText
        )
    }

}