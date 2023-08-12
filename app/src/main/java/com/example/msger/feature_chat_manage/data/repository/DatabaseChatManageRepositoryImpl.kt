package com.example.msger.feature_chat_manage.data.repository

import com.example.msger.feature_chat_manage.data.data_source.local.db.ChatDao
import com.example.msger.feature_chat_manage.data.data_source.local.entity.ChatEntity
import com.example.msger.feature_chat_manage.data.data_source.local.entity.toChat
import com.example.msger.feature_chat_manage.data.data_source.remote.db.RemoteDatabaseChatManage
import com.example.msger.feature_chat_manage.data.data_source.remote.dto.ChatDto
import com.example.msger.feature_chat_manage.data.data_source.remote.dto.MemberDto
import com.example.msger.feature_chat_manage.domain.model.Chat
import com.example.msger.feature_chat_manage.domain.model.toChatDto
import com.example.msger.feature_chat_manage.domain.repository.DatabaseChatManageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DatabaseChatManageRepositoryImpl(
    private val remoteDatabase: RemoteDatabaseChatManage,
    private val localDatabase: ChatDao
) : DatabaseChatManageRepository {
    override val chats: Flow<List<Chat>>
        get() = localDatabase.getAllChats().map { it.map { it.toChat() } }


    override suspend fun getChats(): List<Chat> {
        val chats: List<Map<String, ChatDto>?> = withContext(Dispatchers.IO) {
            remoteDatabase.getChats()
        }

        val chatEntities: List<ChatEntity> = chats.flatMap {
            it?.entries?.map { (chatId, chatInfo) ->
                ChatEntity(
                    name = chatInfo.name ?: "",
                    created = chatInfo.created,
                    chatId = chatId
                )
            } ?: listOf()
        }

        withContext(Dispatchers.IO) {
            localDatabase.upsertChats(chats = chatEntities)
        }
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