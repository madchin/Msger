package com.example.msger.feature_chat_manage.data.repository

import com.example.msger.core.util.Resource
import com.example.msger.feature_chat_manage.data.data_source.local.db.ChatDao
import com.example.msger.feature_chat_manage.data.data_source.local.entity.ChatEntity
import com.example.msger.feature_chat_manage.data.data_source.local.entity.toChat
import com.example.msger.feature_chat_manage.data.data_source.remote.db.RemoteDatabaseChatManage
import com.example.msger.feature_chat_manage.data.data_source.remote.dto.ChatDto
import com.example.msger.feature_chat_manage.data.data_source.remote.dto.MemberDto
import com.example.msger.feature_chat_manage.data.data_source.remote.dto.toChat
import com.example.msger.feature_chat_manage.domain.model.Chat
import com.example.msger.feature_chat_manage.domain.model.toChatDto
import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseChatManageRepositoryImpl(
    private val remoteDatabase: RemoteDatabaseChatManage,
    private val localDatabase: ChatDao
) : DatabaseChatManageRepository {
    override val chats: Flow<Resource<List<Chat>>>
        get() = remoteDatabase.chats.map { result ->
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

    override suspend fun getChats(): List<Chat> {
        val chats: List<Map<String, ChatDto>?> = remoteDatabase.getChats()
        val chatEntities = chats.flatMap {
            it?.entries?.map { (chatId, chatInfo) ->
                ChatEntity(
                    name = chatInfo.name ?: "",
                    created = chatInfo.created,
                    chatId = chatId
                )
            } ?: listOf()
        }

        localDatabase.upsertChats(chats = chatEntities)

        return chatEntities.map { it.toChat() }
    }

    override val currentUserId: String?
        get() = remoteDatabase.currentUserId

    override suspend fun createChat(username: String, chat: Chat): String = remoteDatabase.addChat(
        chat = chat.toChatDto(),
        member = MemberDto(name = username)
    )

    override suspend fun joinChat(username: String, chatId: String) {
        remoteDatabase.updateMemberChat(chatId = chatId, member = MemberDto(name = username))
    }
}