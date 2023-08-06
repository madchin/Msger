package com.example.msger.core.presentation.screens.authorized

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.R
import com.example.msger.data.model.db.ChatEntity
import com.example.msger.data.services.db.DbService
import com.example.msger.core.presentation.navigation.NavigationRoute
import com.example.msger.core.presentation.util.chatNameInputSupportText
import com.example.msger.core.presentation.util.isChatNameValid
import com.example.msger.core.presentation.util.isUsernameValid
import com.example.msger.core.presentation.util.usernameInputSupportText
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
        get() = chatNameInputSupportText(chatName = chatName)
    private val usernameInputErrorText: Int
        get() = usernameInputSupportText(username = username)
    private val isChatNameValid: Boolean
        get() = isChatNameValid(chatName = chatName)

    private val isUsernameValid: Boolean
        get() = isUsernameValid(username = username)

    fun createChat(openAndPopUp: (String,String) -> Unit) {
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
                val chatId = dbService.createChat(username = username, chatEntity = ChatEntity(name = chatName))
                uiState = uiState.copy(isLoading = false)
                openAndPopUp(NavigationRoute.Chat.withArgs(chatId), NavigationRoute.CreateChat.route)
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