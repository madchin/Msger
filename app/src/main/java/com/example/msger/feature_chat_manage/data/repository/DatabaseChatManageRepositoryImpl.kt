package com.example.msger.feature_chat_manage.data.repository

import com.example.msger.core.data.data_source.remote.dto.ChatDto
import com.example.msger.core.data.data_source.remote.dto.ChatMemberDto
import com.example.msger.core.data.data_source.remote.dto.mapToChatEntities
import com.example.msger.core.util.Resource
import com.example.msger.feature_chat_manage.data.data_source.local.db.ChatDao
import com.example.msger.feature_chat_manage.data.data_source.local.entity.ChatEntity
import com.example.msger.feature_chat_manage.data.data_source.local.entity.toChat
import com.example.msger.feature_chat_manage.data.data_source.remote.db.DatabaseChatManage
import com.example.msger.feature_chat_manage.data.data_source.remote.db.DatabaseChatManageImpl
import com.example.msger.feature_chat_manage.domain.model.Chat
import com.example.msger.feature_chat_manage.domain.model.toChatDto
import com.example.msger.feature_chat_manage.domain.model.toChatEntity
import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DatabaseChatManageRepositoryImpl(
    private val remoteDatabase: DatabaseChatManage = DatabaseChatManageImpl(),
    private val localDatabase: ChatDao
) : DatabaseChatManageRepository {

    override fun getAllChats(): Flow<Resource<List<Chat>>> = flow {
        while (true) {
            val localChats: List<ChatEntity> = localDatabase.getAllChats()

            if (localChats.isNotEmpty()) {
                emit(
                    Resource.Success(
                        localDatabase.getAllChats().map { chatEntity -> chatEntity.toChat() })
                )
            } else {
                val remoteChats: List<Map<String, ChatMemberDto>?> = remoteDatabase.getAllChats()

                localDatabase.insertChats(remoteChats.mapToChatEntities())
                emit(Resource.Success(localChats.map { chatEntity -> chatEntity.toChat() }))
            }
            delay(1000)
        }
    }
        .flowOn(Dispatchers.IO)

    override val currentUserId: String?
        get() = remoteDatabase.currentUserId


    override suspend fun createChat(username: String, chat: Chat): String {
        val chatId: String = remoteDatabase.addChat(
            chat = chat.toChatDto(),
            member = ChatMemberDto(username = username)
        )
        remoteDatabase.addMember(
            chatId = chatId,
            member = ChatMemberDto(username = username, chatName = chat.name)
        )
        localDatabase.insertChat(chat = chat.toChatEntity(chatId = chatId, username = username))

        return chatId
    }

    override suspend fun joinChat(username: String, chatId: String) {

        val chatDto: ChatDto = remoteDatabase.updateMember(
            chatId = chatId,
            member = ChatMemberDto(username = username)
        ) ?: throw NoSuchFieldException("Unable to join chat because chat not exists")

        remoteDatabase.updateChat(chat = chatDto)

        localDatabase.insertChat(
            ChatEntity(
                chatId = chatId,
                username = username,
                chatName = chatDto.name,
                lastSeen = Timestamp.now().seconds
            )
        )
    }

    override suspend fun deleteLocalChats() = localDatabase.deleteAllChats()
    override suspend fun joinChatFromChatList(chatId: String) {
        val chatToJoin: ChatEntity = localDatabase.getChat(id = chatId)
        remoteDatabase.updateMember(
            chatId = chatId,
            member = ChatMemberDto(
                lastSeen = Timestamp.now().seconds,
                username = chatToJoin.username,
                chatName = chatToJoin.chatName
            )
        )
        localDatabase.insertChat(chatToJoin.copy(lastSeen = Timestamp.now().seconds))
    }

}