package com.example.msger.feature_chat_manage.data.repository

import com.example.msger.core.data.data_source.remote.dto.ChatDto
import com.example.msger.core.data.data_source.remote.dto.ChatMemberDto
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
import com.google.firebase.Timestamp
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
                    val remoteChats: List<HashMap<String, ChatMemberDto>?> =
                        remoteDatabase.getAllChats()
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
                member = ChatMemberDto(username = username)
            )
            localDatabase.upsertChat(chat = chat.toChatEntity(chatId = chatId))

            chatId
        }
    }

    override suspend fun joinChat(username: String, chatId: String) {
        withContext(Dispatchers.IO) {
            remoteDatabase.updateMemberChat(
                chatId = chatId,
                member = ChatMemberDto(username = username)
            )
            val chat: ChatDto? = remoteDatabase.getChat(chatId = chatId)
            localDatabase.upsertChat(
                ChatEntity(
                    chatName = chat?.name,
                    chatId = chatId,
                    username = username
                )
            )
        }
    }

    override suspend fun deleteLocalChats() {
        withContext(Dispatchers.IO) {
            localDatabase.deleteAllChats()
        }
    }

    override suspend fun joinChatFromChatList(chatId: String) {
        withContext(Dispatchers.IO) {
            val chatToJoin: ChatEntity = localDatabase.getChat(id = chatId)
            remoteDatabase.updateMemberChat(
                chatId = chatId,
                member = ChatMemberDto(
                    lastSeen = Timestamp.now().seconds,
                    username = chatToJoin.username,
                    chatName = chatToJoin.chatName
                )
            )
            localDatabase.upsertChat(chatToJoin.copy(lastSeen = Timestamp.now().seconds))
        }
    }

}