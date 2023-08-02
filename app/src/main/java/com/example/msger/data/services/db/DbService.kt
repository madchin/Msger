package com.example.msger.data.services.db

import com.example.msger.data.model.db.ChatEntity
import kotlinx.coroutines.flow.Flow

interface DbService {

    val chats: Flow<Result<List<ChatEntity>>>
    suspend fun createChat(username: String, chatEntity: ChatEntity)

}