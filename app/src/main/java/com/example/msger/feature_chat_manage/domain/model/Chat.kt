package com.example.msger.feature_chat_manage.domain.model

import com.example.msger.feature_chat_manage.data.data_source.local.entity.ChatEntity
import com.example.msger.core.data.data_source.remote.dto.ChatDto

data class Chat(
    val name: String,
    val id: String? = null,
    val lastSeen: Long? = null
)

fun Chat.toChatDto() = ChatDto(name = name)

fun Chat.toChatEntity(chatId: String) = ChatEntity(chatName = name, lastSeen = lastSeen, chatId = chatId)

