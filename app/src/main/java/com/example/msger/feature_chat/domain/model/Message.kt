package com.example.msger.feature_chat.domain.model

data class Message(
    val content: String,
    val timestamp: Long,
    val sender: String,
    val key: String
)
