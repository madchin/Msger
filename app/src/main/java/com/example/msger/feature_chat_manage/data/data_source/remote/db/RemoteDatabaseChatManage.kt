package com.example.msger.feature_chat_manage.data.data_source.remote.db

import com.example.msger.core.util.Resource
import com.example.msger.feature_chat_manage.data.data_source.remote.dto.ChatDto
import com.example.msger.feature_chat_manage.data.data_source.remote.dto.MemberDto

interface RemoteDatabaseChatManage {

    val currentUserId: String?
    suspend fun addChat(chat: ChatDto, member: MemberDto): String
    suspend fun updateMemberChat(chatId: String, member: MemberDto)

    suspend fun getAllChats(): Resource<List<Map<String, ChatDto>?>>
    suspend fun addMemberChat(chatId: String, member: MemberDto)
}