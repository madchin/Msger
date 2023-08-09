package com.example.msger.feature_chat_manage.data.data_source.db

import com.example.msger.feature_chat_manage.data.data_source.dto.ChatDto
import com.example.msger.feature_chat_manage.data.data_source.dto.MemberDto
import kotlinx.coroutines.flow.Flow

interface DatabaseChatManage {

    val chats: Flow<Result<List<ChatDto>>>
    val currentUserId: String?
    suspend fun addChat(chat: ChatDto, member: MemberDto): String
    suspend fun updateMemberChat(chatId: String, member: MemberDto)

    suspend fun addMemberChat(chatId: String, member: MemberDto)
}