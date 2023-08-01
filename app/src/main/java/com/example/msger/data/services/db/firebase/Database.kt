package com.example.msger.data.services.db.firebase

import com.example.msger.data.model.Chat
import kotlinx.coroutines.flow.Flow

interface Database {
    val chats: Flow<Result<List<Chat>>>
    suspend fun createChat(chat: Chat)

}