package com.example.msger.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(val id: String, val chats: List<Chat>? = null)

