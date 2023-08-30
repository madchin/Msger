package com.example.msger.feature_chat_manage.data.data_source.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.msger.feature_chat_manage.domain.model.Chat
import com.google.firebase.Timestamp

@Entity(tableName = "chat", indices = arrayOf(Index(value = arrayOf("chatId"), unique = true)))
data class ChatEntity(
    @PrimaryKey val id: Int? = null,
    val chatName: String? = null,
    val username: String? = null,
    val lastSeen: Long? = Timestamp.now().seconds,
    val chatId: String? = null
)

fun ChatEntity.toChat() = Chat(name = chatName ?: "", lastSeen = lastSeen, id = chatId ?: "")