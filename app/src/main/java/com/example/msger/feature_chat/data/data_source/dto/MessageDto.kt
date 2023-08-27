package com.example.msger.feature_chat.data.data_source.dto

import com.google.firebase.Timestamp

data class MessageDto(
    val content: String? = null,
    val sender: String? = null,
    val timestamp: Long = Timestamp.now().seconds
)
