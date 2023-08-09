package com.example.msger.feature_chat.data.data_source.db

import com.example.msger.feature_chat.data.data_source.dto.MemberDto
import kotlinx.coroutines.flow.Flow

interface DatabaseChat {

    fun getChatMembers(chatId: String): Flow<Result<List<Map<String, MemberDto>?>>>

}