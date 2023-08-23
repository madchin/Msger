package com.example.msger.feature_chat_manage.data.data_source.remote.db

import com.example.msger.core.data.data_source.remote.dto.ChatDto
import com.example.msger.core.data.data_source.remote.dto.MemberDto

interface RemoteDatabaseChatManage {

    val currentUserId: String?
    suspend fun addChat(chat: ChatDto, member: MemberDto): String
    suspend fun updateMemberChat(chatId: String, member: MemberDto)

    suspend fun getAllChats(): List<HashMap<String, MemberDto>?>
    suspend fun addMemberChat(chatId: String, member: MemberDto)
}