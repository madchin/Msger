package com.example.msger.data.services.db

import com.example.msger.feature_chat_manage.domain.model.Chat
import com.example.msger.core.data.model.Member
import com.example.msger.data.services.db.firebase.Database
import kotlinx.coroutines.flow.Flow

class DbServiceImpl(
    private val database: Database
) : DbService {
    override val chats: Flow<Result<List<Chat>>>
        get() = database.chats

    override val currentUserId: String?
        get() = database.currentUserId
    override fun getChatMembers(chatId: String): Flow<Result<List<Map<String, Member>?>>> =
        database.getChatMembers(chatId)

    override suspend fun createChat(username: String, chat: Chat): String =
        database.createChat(username, chat)

}