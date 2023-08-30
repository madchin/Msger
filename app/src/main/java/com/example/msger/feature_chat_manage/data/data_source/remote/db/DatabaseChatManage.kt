package com.example.msger.feature_chat_manage.data.data_source.remote.db

import com.example.msger.core.data.data_source.remote.dto.ChatDto
import com.example.msger.core.data.data_source.remote.dto.ChatMemberDto

interface DatabaseChatManage {

    val currentUserId: String?
    suspend fun addChat(chat: ChatDto, member: ChatMemberDto): String
    suspend fun updateMemberChat(chatId: String, member: ChatMemberDto)

    suspend fun getAllChats(): List<Map<String, ChatMemberDto>?>
    suspend fun addMemberChat(chatId: String, member: ChatMemberDto)

    suspend fun getChat(chatId: String): ChatDto?
}