package com.example.msger.feature_chat_manage.data.repository

import com.example.msger.core.data.data_source.remote.dto.MemberDto
import com.example.msger.core.data.data_source.remote.dto.mapToChatEntities
import com.example.msger.core.data.data_source.remote.dto.mapToChats
import com.example.msger.core.util.Resource
import com.example.msger.feature_chat_manage.data.data_source.local.db.ChatDao
import com.example.msger.feature_chat_manage.data.data_source.local.entity.ChatEntity
import com.example.msger.feature_chat_manage.data.data_source.local.entity.toChat
import com.example.msger.feature_chat_manage.data.data_source.remote.db.RemoteDatabaseChatManage
import com.example.msger.feature_chat_manage.domain.model.Chat
import com.example.msger.feature_chat_manage.domain.model.toChatDto
import com.example.msger.feature_chat_manage.domain.model.toChatEntity
import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DatabaseChatManageRepositoryImpl(
    private val remoteDatabase: RemoteDatabaseChatManage,
    private val localDatabase: ChatDao
) : DatabaseChatManageRepository {

    override fun getAllChats(): Flow<Resource<List<Chat>>> = localDatabase
        .getAllChats()
        .map {
            when {
                it.isEmpty() -> {
                    val remoteChats: List<HashMap<String, MemberDto>?> = remoteDatabase.getAllChats()
                    localDatabase.upsertChats(chats = remoteChats.mapToChatEntities())
                    Resource.Success(data = remoteChats.mapToChats())
                }
                else -> Resource.Success(data = it.map { chatEntity -> chatEntity.toChat() })
            }
        }

    override val currentUserId: String?
        get() = remoteDatabase.currentUserId


    override suspend fun createChat(username: String, chat: Chat): String {
        return withContext(Dispatchers.IO) {
            val chatId: String = remoteDatabase.addChat(
                chat = chat.toChatDto(),
                member = MemberDto(name = username)
            )
            localDatabase.upsertChat(chat = chat.toChatEntity(chatId = chatId))

            chatId
        }
    }

    override suspend fun joinChat(username: String, chatId: String) {
        withContext(Dispatchers.IO) {
            remoteDatabase.updateMemberChat(chatId = chatId, member = MemberDto(name = username))
            localDatabase.upsertChat(ChatEntity(name = "xD", chatId = chatId))
        }
    }

    override suspend fun deleteLocalChats() {
        withContext(Dispatchers.IO) {
            localDatabase.deleteAllChats()
        }
    }

}