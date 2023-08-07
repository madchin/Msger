package com.example.msger.feature_chat_manage.data.repository

import com.example.msger.feature_chat_manage.data.data_source.db.DatabaseChatManage
import com.example.msger.feature_chat_manage.domain.model.Chat
import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository
import kotlinx.coroutines.flow.Flow

class DatabaseChatManageRepositoryImpl(
    private val database: DatabaseChatManage
) : DatabaseChatManageRepository {
    override val chats: Flow<Result<List<Chat>>>
        get() = database.chats

    override val currentUserId: String?
        get() = database.currentUserId

    override suspend fun createChat(username: String, chat: Chat): String =
        database.createChat(username, chat)

    override suspend fun joinChat(username: String, chatId: String) {
        database.joinChat(username, chatId)
    }
}