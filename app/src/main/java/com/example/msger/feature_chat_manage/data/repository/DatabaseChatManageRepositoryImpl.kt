package com.example.msger.feature_chat_manage.data.repository

import com.example.msger.core.util.Resource
import com.example.msger.feature_chat_manage.data.data_source.local.db.ChatDao
import com.example.msger.feature_chat_manage.data.data_source.local.entity.ChatEntity
import com.example.msger.feature_chat_manage.data.data_source.local.entity.toChat
import com.example.msger.feature_chat_manage.data.data_source.remote.db.RemoteDatabaseChatManage
import com.example.msger.feature_chat_manage.data.data_source.remote.dto.MemberDto
import com.example.msger.feature_chat_manage.domain.model.Chat
import com.example.msger.feature_chat_manage.domain.model.toChatDto
import com.example.msger.feature_chat_manage.domain.model.toChatEntity
import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseChatManageRepositoryImpl(
    private val remoteDatabase: RemoteDatabaseChatManage,
    private val localDatabase: ChatDao
) : DatabaseChatManageRepository {

    override val currentUserId: String?
        get() = remoteDatabase.currentUserId

    override suspend fun getAllChats(): Resource<List<Chat>> {
        val localChats = withContext(Dispatchers.IO) { localDatabase.getAllChats() }
        val remoteChats = remoteDatabase.getAllChats()
        if (localChats.isNotEmpty()) localChats.map { it.toChat() }

        return when (remoteChats) {
            is Resource.Success -> Resource.Success(
                remoteChats
                    .data
                    ?.filterNotNull()
                    ?.map {
                        it
                            .values
                            .map { Chat(name = it.name ?: "") }
                    }?.flatten()
            )

            is Resource.Error -> Resource.Error(message = remoteChats.message ?: "generic")
            else -> Resource.Loading()
        }
    }

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
        localDatabase.deleteAllChats()
    }

}