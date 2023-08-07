package com.example.msger.feature_chat_manage.data.data_source.db

import com.example.msger.feature_chat_manage.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface DatabaseChatManage {

    val chats: Flow<Result<List<Chat>>>
    val currentUserId: String?

    suspend fun createChat(username: String, chat: Chat): String

    suspend fun joinChat(username: String, chatId: String)
}