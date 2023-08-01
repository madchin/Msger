package com.example.msger.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Chat(
    val name: String? = null,
    val members: MutableMap<String, Boolean>? = null
)
