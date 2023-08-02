package com.example.msger.data.model.db

import com.google.firebase.Timestamp
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ChatEntity(
    val name: String? = null,
    val created: Long = Timestamp.now().seconds
)
