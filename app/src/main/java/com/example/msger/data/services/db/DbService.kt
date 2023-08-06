package com.example.msger.data.services.db

import com.example.msger.feature_chat_manage.domain.model.Chat
import com.example.msger.core.data.model.Member
import kotlinx.coroutines.flow.Flow

interface DbService {

    val chats: Flow<Result<List<Chat>>>
    val currentUserId: String?

    fun getChatMembers(chatId: String): Flow<Result<List<Map<String, Member>?>>>
    suspend fun createChat(username: String, chat: Chat): String

}