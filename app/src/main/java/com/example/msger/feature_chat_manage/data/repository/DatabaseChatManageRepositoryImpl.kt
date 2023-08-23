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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DatabaseChatManageRepositoryImpl(
    private val remoteDatabase: RemoteDatabaseChatManage,
    private val localDatabase: ChatDao
) : DatabaseChatManageRepository {

    private val localChats: Flow<Resource<List<Chat>>> = localDatabase
        .getAllChats()
        .map {
            when {
                it.isEmpty() -> Resource.Loading()
                else -> Resource.Success(data = it.map { chatEntity -> chatEntity.toChat() })
            }
        }

    private val remoteChats: Flow<Resource<List<Chat>>> = remoteDatabase.getAllChats().map {
        when (it) {
            is Resource.Success ->
                Resource.Success(it.data?.flatMap { chats ->
                    chats?.values?.map { chat ->
                        Chat(
                            name = chat.name ?: ""
                        )
                    } ?: listOf()
                })


            is Resource.Error -> Resource.Error(message = it.message ?: "generic")
            is Resource.Loading -> Resource.Loading()
        }
    }

    override val currentUserId: String?
        get() = remoteDatabase.currentUserId

    override fun getAllChats(): Flow<Resource<List<Chat>>> =
        combine(localChats, remoteChats) { local, remote ->
            if (local.data?.isEmpty() == true) remote else local
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
        withContext(Dispatchers.IO) {
            localDatabase.deleteAllChats()
        }
    }

}