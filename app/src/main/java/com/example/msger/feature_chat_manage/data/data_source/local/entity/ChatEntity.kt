package com.example.msger.feature_chat_manage.data.data_source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.msger.feature_chat_manage.domain.model.Chat

@Entity(tableName = "chat")
data class ChatEntity(
    @PrimaryKey val id: Int? = null,
    val name: String? = null,
    val created: Long? = null,
    val chatId: String? = null
)

fun ChatEntity.toChat() = Chat(name = name ?: "", created = created)