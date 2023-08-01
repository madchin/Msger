package com.example.msger.data.services.db

import com.example.msger.data.model.Chat
import com.example.msger.data.services.db.firebase.Database
import kotlinx.coroutines.flow.Flow

class DbServiceImpl(
    private val dbService: Database
) : DbService {
    override val chats: Flow<Result<List<Chat>>>
        get() = dbService.chats


    override suspend fun createChat(username: String, chat: Chat) {
        dbService.createChat(username, chat)
    }
}