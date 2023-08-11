package com.example.msger.feature_chat_manage.data.data_source.remote.db

import com.example.msger.feature_chat_manage.data.data_source.remote.dto.ChatDto
import com.example.msger.feature_chat_manage.data.data_source.remote.dto.MemberDto
import kotlinx.coroutines.flow.Flow

interface RemoteDatabaseChatManage {

    val chats: Flow<Result<List<ChatDto>>>
    val currentUserId: String?
    suspend fun addChat(chat: ChatDto, member: MemberDto): String
    suspend fun updateMemberChat(chatId: String, member: MemberDto)

    suspend fun getChats(): List<Map<String, ChatDto>?>
    suspend fun addMemberChat(chatId: String, member: MemberDto)
}