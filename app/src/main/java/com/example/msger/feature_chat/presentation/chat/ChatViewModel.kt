package com.example.msger.feature_chat.presentation.chat

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class ChatViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val chatId: String = checkNotNull(savedStateHandle["chatId"])

    init {
        Log.d("CHAT", "chat id from saved state handle is: $chatId")
    }
}