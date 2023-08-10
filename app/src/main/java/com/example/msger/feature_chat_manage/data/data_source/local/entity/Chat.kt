package com.example.msger.feature_chat_manage.data.data_source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat")
data class Chat(
    @PrimaryKey val id: Int,
    val name: String,
    val created: Long,
    val chatId: String
)