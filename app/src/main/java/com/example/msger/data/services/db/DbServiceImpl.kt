package com.example.msger.data.services.db

import com.example.msger.data.model.db.ChatEntity
import com.example.msger.data.model.db.MemberEntity
import com.example.msger.data.services.db.firebase.Database
import kotlinx.coroutines.flow.Flow

class DbServiceImpl(
    private val database: Database
) : DbService {
    override val chats: Flow<Result<List<ChatEntity>>>
        get() = database.chats

    override val members: Flow<Result<List<MemberEntity>>>
        get() = database.members

    override suspend fun createChat(username: String, chatEntity: ChatEntity): String =
        database.createChat(username, chatEntity)

}