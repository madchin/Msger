package com.example.msger.feature_chat_manage.presentation.util

import com.example.msger.R


fun usernameInputSupportText(username: String): Int =
    if (isUsernameValid(username = username)) R.string.input_required else R.string.input_blank_validation

fun chatNameInputSupportText(chatName: String): Int =
    if (isChatNameValid(chatName = chatName)) R.string.input_required else R.string.input_blank_validation