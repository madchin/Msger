package com.example.msger.data.services.db.firebase

import com.example.msger.data.model.db.ChatEntity
import kotlinx.coroutines.flow.Flow

interface Database {

    val chats: Flow<Result<List<ChatEntity>>>
    suspend fun createChat(username: String, chatEntity: ChatEntity): String

}