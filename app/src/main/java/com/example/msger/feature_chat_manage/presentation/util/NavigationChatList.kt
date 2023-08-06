package com.example.msger.feature_chat_manage.presentation.util

sealed class NavigationChatManage(val route: String) {

    object ChatList : NavigationChatManage(route = "home")

    object CreateChat : NavigationChatManage(route = "create-chat")

    object JoinChat : NavigationChatManage(route = "join-chat")
}