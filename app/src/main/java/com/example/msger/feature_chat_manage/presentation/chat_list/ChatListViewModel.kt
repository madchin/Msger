package com.example.msger.feature_chat_manage.presentation.chat_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.core.presentation.navigation.NavigationRoute
import com.example.msger.core.util.Resource
import com.example.msger.feature_chat_manage.domain.model.Chat
import com.example.msger.feature_chat_manage.domain.use_case.GetChatsUseCase
import com.example.msger.feature_chat_manage.domain.use_case.JoinChatUseCase
import com.example.msger.feature_chat_manage.domain.use_case.SignOutUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val joinChatUseCase: JoinChatUseCase,
    getChatsUseCase: GetChatsUseCase
) : ViewModel() {

    private val _chats: StateFlow<Resource<List<Chat>>> = getChatsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Resource.Loading()
    )
    val chats: StateFlow<Resource<List<Chat>>> = _chats

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