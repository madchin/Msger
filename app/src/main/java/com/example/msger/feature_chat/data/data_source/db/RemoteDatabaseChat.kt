package com.example.msger.feature_chat.data.data_source.db

import com.example.msger.core.data.data_source.remote.dto.ChatMemberDto
import com.example.msger.core.util.Resource
import com.example.msger.feature_chat.data.data_source.dto.MessageDto
import kotlinx.coroutines.flow.Flow

interface RemoteDatabaseChat {

    suspend fun getChatMemberInfo(chatId: String, userUid: String): ChatMemberDto?

    fun getChatMembersUid(chatId: String): Flow<List<String>>

    fun getChatMessages(chatId: String): Flow<Resource<List<Map<String, MessageDto>?>>>

    suspend fun addMessage(chatId: String, content: String)
}