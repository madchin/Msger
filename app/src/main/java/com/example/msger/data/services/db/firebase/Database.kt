package com.example.msger.data.services.db.firebase

import com.example.msger.data.model.db.ChatEntity
import com.example.msger.data.model.db.MemberEntity
import kotlinx.coroutines.flow.Flow

interface Database {

    val chats: Flow<Result<List<ChatEntity>>>

    val members: Flow<Result<List<MemberEntity>>>
    suspend fun createChat(username: String, chatEntity: ChatEntity): String

}