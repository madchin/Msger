package com.example.msger.core.presentation.screens.authorized

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository

class ChatViewModel(savedStateHandle: SavedStateHandle, private val databaseChatManageRepository: DatabaseChatManageRepository) : ViewModel() {

    val chatId: String = checkNotNull(savedStateHandle["chatId"])

    init {
        Log.d("CHAT", "chat id from saved state handle is: $chatId")
    }
}