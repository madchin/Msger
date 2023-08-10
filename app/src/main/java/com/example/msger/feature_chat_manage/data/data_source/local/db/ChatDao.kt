package com.example.msger.feature_chat_manage.data.data_source.local.db

import androidx.room.Dao
import androidx.room.Query
import com.example.msger.feature_chat_manage.data.data_source.local.entity.Chat

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat")
    fun getAll(): List<Chat>

    @Query("SELECT * FROM chat WHERE id=:id")
    fun getChat(id: String): Chat
}