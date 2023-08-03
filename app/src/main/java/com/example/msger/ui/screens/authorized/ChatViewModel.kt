package com.example.msger.ui.screens.authorized

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.msger.data.services.db.DbService

class ChatViewModel(savedStateHandle: SavedStateHandle, private val dbService: DbService) : ViewModel() {

    val chatId: String = checkNotNull(savedStateHandle["chatId"])

    init {
        Log.d("CHAT", "chat id from saved state handle is: $chatId")
    }
}