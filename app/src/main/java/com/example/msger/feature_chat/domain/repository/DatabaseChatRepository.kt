package com.example.msger.feature_chat.domain.repository

import com.example.msger.core.util.Resource
import com.example.msger.core.domain.model.Member
import com.example.msger.feature_chat.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface DatabaseChatRepository {
    fun getChatMembers(chatId: String):  Flow<Resource<List<Member>>>

    fun getChatMessages(chatId: String): Flow<Resource<List<Message>>>

    suspend fun sendMessage(chatId: String, content: String)
}