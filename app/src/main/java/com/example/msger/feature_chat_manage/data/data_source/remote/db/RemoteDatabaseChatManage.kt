package com.example.msger.feature_chat_manage.data.data_source.remote.db

import com.example.msger.core.data.data_source.remote.dto.ChatDto
import com.example.msger.core.data.data_source.remote.dto.MemberDto
import com.example.msger.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteDatabaseChatManage {

    val currentUserId: String?
    suspend fun addChat(chat: ChatDto, member: MemberDto): String
    suspend fun updateMemberChat(chatId: String, member: MemberDto)

    fun getAllChats(): Flow<Resource<List<Map<String, MemberDto>?>>>
    suspend fun addMemberChat(chatId: String, member: MemberDto)
}