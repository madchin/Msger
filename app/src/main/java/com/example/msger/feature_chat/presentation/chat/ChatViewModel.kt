package com.example.msger.feature_chat.presentation.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.core.util.Resource
import com.example.msger.feature_chat.domain.model.Message
import com.example.msger.feature_chat.domain.use_case.GetChatMessagesUseCase
import com.example.msger.feature_chat.domain.use_case.SendMessageUseCase
import com.example.msger.feature_chat.presentation.util.isChatMessageValid
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatViewModel(
    savedStateHandle: SavedStateHandle,
    getChatMessagesUseCase: GetChatMessagesUseCase,
    private val sendMessageUseCase: SendMessageUseCase
) : ViewModel() {

    val chatId: String = checkNotNull(savedStateHandle["chatId"])
    private val _messages: StateFlow<Resource<List<Message>>> =
        getChatMessagesUseCase(chatId = chatId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Resource.Loading()
        )
    val messages: StateFlow<Resource<List<Message>>> = _messages

    var inputValue: String by mutableStateOf("")

    var isInputValid: Boolean by mutableStateOf(true)

    fun onInputChange(value: String) {
        inputValue = value
        isInputValid = isChatMessageValid(message = inputValue)
    }

    fun sendMessage() {
        viewModelScope.launch {
            if (inputValue.isNotBlank()) {
                sendMessageUseCase(chatId = chatId, content = inputValue)
                inputValue = ""
            }
        }
    }
}