package com.example.msger.feature_chat.domain.model

import com.google.firebase.Timestamp

data class Message(
    val content: String,
    val sender: String,
    val timestamp: Long = Timestamp.now().seconds
)
