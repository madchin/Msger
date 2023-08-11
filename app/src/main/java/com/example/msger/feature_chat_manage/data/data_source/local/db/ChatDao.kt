package com.example.msger.feature_chat_manage.data.data_source.local.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.msger.feature_chat_manage.data.data_source.local.entity.ChatEntity

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat")
    fun getAll(): List<ChatEntity>

    @Query("SELECT * FROM chat WHERE id=:id")
    fun getChat(id: String): ChatEntity

    @Upsert
    fun upsertChat(chat: ChatEntity)

    @Upsert
    fun upsertChats(chats: List<ChatEntity>)
}