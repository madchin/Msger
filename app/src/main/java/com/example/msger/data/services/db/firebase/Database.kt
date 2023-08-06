package com.example.msger.data.services.db.firebase

import com.example.msger.feature_chat_manage.domain.model.Chat
import com.example.msger.core.data.model.Member
import kotlinx.coroutines.flow.Flow

interface Database {

    val chats: Flow<Result<List<Chat>>>
    val currentUserId: String?
    fun getChatMembers(chatId: String): Flow<Result<List<Map<String, Member>?>>>
    suspend fun createChat(username: String, chat: Chat): String

}