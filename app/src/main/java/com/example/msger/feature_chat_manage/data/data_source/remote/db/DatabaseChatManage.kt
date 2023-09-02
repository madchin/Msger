package com.example.msger.feature_chat_manage.data.data_source.remote.db

import com.example.msger.core.data.data_source.remote.dto.ChatDto
import com.example.msger.core.data.data_source.remote.dto.ChatMemberDto

interface DatabaseChatManage {

    val currentUserId: String?
    suspend fun addChat(chat: ChatDto, member: ChatMemberDto): String
    suspend fun updateMember(chatId: String, member: ChatMemberDto): ChatDto?

    suspend fun updateChat(chat: ChatDto)
    suspend fun getAllChats(): List<Map<String, ChatMemberDto>?>
    suspend fun addMember(chatId: String, member: ChatMemberDto)

    suspend fun getChat(chatId: String): ChatDto?
}