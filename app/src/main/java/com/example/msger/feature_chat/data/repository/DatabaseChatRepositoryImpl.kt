package com.example.msger.feature_chat.data.repository

import com.example.msger.feature_chat.domain.model.Member
import com.example.msger.feature_chat.data.data_source.DatabaseChat
import com.example.msger.feature_chat.domain.repository.DatabaseChatRepository
import kotlinx.coroutines.flow.Flow

class DatabaseChatRepositoryImpl(
    private val dbRepository: DatabaseChat
) : DatabaseChatRepository {
    override fun getChatMembers(chatId: String): Flow<Result<List<Map<String, Member>?>>> =
        dbRepository.getChatMembers(chatId)
}