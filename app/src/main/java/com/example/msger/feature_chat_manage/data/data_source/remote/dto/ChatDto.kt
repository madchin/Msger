package com.example.msger.feature_chat_manage.data.data_source.remote.dto

import com.example.msger.feature_chat_manage.domain.model.Chat
import com.google.firebase.Timestamp

data class ChatDto(
    val name: String? = null,
    val created: Long = Timestamp.now().seconds
)

fun ChatDto.toChat() = Chat(name = name ?: "", created = created)