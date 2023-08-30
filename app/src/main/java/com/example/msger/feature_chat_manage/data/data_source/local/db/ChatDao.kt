package com.example.msger.feature_chat_manage.data.data_source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.msger.feature_chat_manage.data.data_source.local.entity.ChatEntity

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat ORDER BY lastSeen DESC")
    suspend fun getAllChats(): List<ChatEntity>

    @Query("SELECT * FROM chat WHERE chatId=:id")
    suspend fun getChat(id: String): ChatEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chat: ChatEntity)

    @Insert
    suspend fun insertChats(chats: List<ChatEntity>)

    @Query("DELETE FROM chat")
    suspend fun deleteAllChats()
}