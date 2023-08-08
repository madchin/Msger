package com.example.msger.feature_chat_manage.presentation.util
fun isChatNameValid(chatName: String): Boolean = chatName.isEmpty() || chatName.isNotBlank()

fun isUsernameValid(username: String): Boolean = username.isEmpty() || username.isNotBlank()

fun isChatIdValid(chatId: String): Boolean = chatId.isEmpty() || chatId.isNotBlank()