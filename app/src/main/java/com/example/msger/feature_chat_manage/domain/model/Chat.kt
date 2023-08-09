package com.example.msger.feature_chat_manage.domain.model

import com.example.msger.feature_chat_manage.data.data_source.dto.ChatDto

data class Chat(
    val name: String,
    val created: Long
)

fun Chat.toChatDto() = ChatDto(name = name)
