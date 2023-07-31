package com.example.msger.data.services.db

import com.example.msger.data.model.Chat
import kotlinx.coroutines.flow.Flow

interface DbService {
    val chats: Flow<Result<List<Chat>>>
    suspend fun createChat()
}