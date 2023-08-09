package com.example.msger.feature_chat.domain.repository

import com.example.msger.core.util.Resource
import com.example.msger.feature_chat.domain.model.Member
import kotlinx.coroutines.flow.Flow

interface DatabaseChatRepository {
    fun getChatMembers(chatId: String):  Flow<Resource<List<Member>>>
}