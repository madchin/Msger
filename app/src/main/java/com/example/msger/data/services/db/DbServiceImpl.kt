package com.example.msger.data.services.db

import com.example.msger.data.services.db.firebase.Database

class DbServiceImpl(private val dbService: Database) : DbService {
    override suspend fun createChat() {
        dbService.createChat()
    }
}