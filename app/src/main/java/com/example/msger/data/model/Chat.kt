package com.example.msger.data.model

data class Chat(
    val name: String? = null,
    val members: MutableMap<String, Boolean>? = null
)
