package com.example.msger.feature_chat_manage.data.data_source.remote.dto

import com.example.msger.feature_chat_manage.data.data_source.local.entity.ChatEntity
import com.example.msger.feature_chat_manage.domain.model.Chat
import com.google.firebase.Timestamp

data class ChatDto(
    val name: String? = null,
    val created: Long = Timestamp.now().seconds,
    val chatId: String? = null
)

fun ChatDto.toChat() = Chat(name = name ?: "", created = created)

fun ChatDto.toChatEntity() = ChatEntity(name = name, created = created, chatId = chatId)