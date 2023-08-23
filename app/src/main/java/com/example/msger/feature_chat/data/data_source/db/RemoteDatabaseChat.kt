package com.example.msger.feature_chat.data.data_source.db

import com.example.msger.core.data.data_source.remote.dto.MemberDto
import kotlinx.coroutines.flow.Flow

interface RemoteDatabaseChat {

    fun getChatMembers(chatId: String): Flow<Result<List<Map<String, MemberDto>?>>>

}