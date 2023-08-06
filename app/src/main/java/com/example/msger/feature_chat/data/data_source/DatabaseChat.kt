package com.example.msger.feature_chat.data.data_source

import com.example.msger.feature_chat.domain.model.Member
import kotlinx.coroutines.flow.Flow

interface DatabaseChat {

    fun getChatMembers(chatId: String): Flow<Result<List<Map<String, Member>?>>>

}