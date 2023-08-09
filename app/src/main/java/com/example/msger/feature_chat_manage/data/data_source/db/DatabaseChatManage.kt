package com.example.msger.feature_chat_manage.data.data_source.db

import com.example.msger.feature_chat_manage.data.data_source.dto.ChatDto
import com.example.msger.feature_chat_manage.data.data_source.dto.MemberDto
import kotlinx.coroutines.flow.Flow

interface DatabaseChatManage {

    val chats: Flow<Result<List<ChatDto>>>
    val currentUserId: String?
    suspend fun addChat(chat: ChatDto): String
    suspend fun updateMemberChats(chatId: String, member: MemberDto)
}