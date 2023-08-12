package com.example.msger.feature_chat_manage.domain.model

import com.example.msger.feature_chat_manage.data.data_source.remote.dto.ChatDto

data class Chat(
    val name: String,
    val created: Long? = null
)

fun Chat.toChatDto() = ChatDto(name = name)

