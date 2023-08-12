package com.example.msger.feature_chat_manage.data.data_source.local.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.msger.feature_chat_manage.data.data_source.local.entity.ChatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat")
    fun getAllChats(): Flow<List<ChatEntity>>

    @Query("SELECT * FROM chat WHERE id=:id")
    suspend fun getChat(id: String): ChatEntity

    @Upsert
    suspend fun upsertChat(chat: ChatEntity)

    @Upsert
    suspend fun upsertChats(chats: List<ChatEntity>)
}