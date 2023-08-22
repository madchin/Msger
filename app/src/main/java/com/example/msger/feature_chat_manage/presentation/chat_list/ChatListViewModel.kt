package com.example.msger.feature_chat_manage.presentation.chat_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.core.presentation.navigation.NavigationRoute
import com.example.msger.core.util.Resource
import com.example.msger.feature_chat_manage.domain.model.Chat
import com.example.msger.feature_chat_manage.domain.use_case.GetChatsUseCase
import com.example.msger.feature_chat_manage.domain.use_case.JoinChatUseCase
import com.example.msger.feature_chat_manage.domain.use_case.SignOutUseCase
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val joinChatUseCase: JoinChatUseCase,
    getChatsUseCase: GetChatsUseCase
) : ViewModel() {

    private var _chats: Resource<List<Chat>> by mutableStateOf(Resource.Loading())
    val chats: Resource<List<Chat>> = _chats

    init {
        viewModelScope.launch {
            _chats = getChatsUseCase()
        }
    }

    fun signOut(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            try {
                signOutUseCase()
                openAndPopUp(NavigationRoute.SignIn.route, NavigationRoute.ChatList.route)
            } catch (e: Throwable) {
                // TODO: catch errors properly
            }
        }
    }

    fun joinChat(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            try {

                openAndPopUp(
                    NavigationRoute.Chat.withArgs(""),
                    NavigationRoute.ChatList.route
                )
            } catch (e: Throwable) {

            }
        }
    }
}