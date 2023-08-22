package com.example.msger.feature_chat_manage.domain.repository

import com.example.msger.core.util.Resource
import com.example.msger.feature_chat_manage.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface DatabaseChatManageRepository {
    val currentUserId: String?

    suspend fun createChat(username: String, chat: Chat): String
    suspend fun joinChat(username: String, chatId: String)

    fun getAllChats(): Flow<Resource<List<Chat>>>

    suspend fun deleteLocalChats()
}