package com.example.msger.feature_chat.presentation.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.core.util.Resource
import com.example.msger.feature_chat.domain.model.Message
import com.example.msger.feature_chat.domain.use_case.GetChatMessagesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ChatViewModel(
    savedStateHandle: SavedStateHandle,
    getChatMessagesUseCase: GetChatMessagesUseCase
) : ViewModel() {

    val chatId: String = checkNotNull(savedStateHandle["chatId"])
    private val _messages: StateFlow<Resource<List<Message>>> =
        getChatMessagesUseCase(chatId = chatId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Resource.Loading()
        )
    val messages: StateFlow<Resource<List<Message>>> = _messages

}