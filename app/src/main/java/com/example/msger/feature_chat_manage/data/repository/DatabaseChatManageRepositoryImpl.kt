package com.example.msger.feature_chat_manage.data.repository

import com.example.msger.core.util.Resource
import com.example.msger.feature_chat_manage.data.data_source.db.DatabaseChatManage
import com.example.msger.feature_chat_manage.data.data_source.dto.MemberDto
import com.example.msger.feature_chat_manage.data.data_source.dto.toChat
import com.example.msger.feature_chat_manage.domain.model.Chat
import com.example.msger.feature_chat_manage.domain.model.toChatDto
import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseChatManageRepositoryImpl(
    private val database: DatabaseChatManage
) : DatabaseChatManageRepository {
    override val chats: Flow<Resource<List<Chat>>>
        get() = database.chats.map { result ->
            when {
                result.isSuccess -> {
                    val chats: List<Chat> = result.getOrDefault(listOf()).map { it.toChat() }
                    Resource.Success(chats)
                }

                result.isFailure -> {
                    val error: Throwable? = result.exceptionOrNull()
                    Resource.Error(error?.message.toString())
                }

                else -> Resource.Loading()
            }
        }

    override val currentUserId: String?
        get() = database.currentUserId

    override suspend fun createChat(username: String, chat: Chat): String = database.addChat(
        chat = chat.toChatDto(),
        member = MemberDto(name = username)
    )

    override suspend fun joinChat(username: String, chatId: String) {
        database.updateMemberChat(chatId = chatId, member = MemberDto(name = username))
    }
}