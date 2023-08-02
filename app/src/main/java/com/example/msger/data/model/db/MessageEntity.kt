package com.example.msger.data.model.db

import com.google.firebase.Timestamp

data class MessageEntity(
    val content: String,
    val sender: String,
    val timestamp: Long = Timestamp.now().seconds
)
