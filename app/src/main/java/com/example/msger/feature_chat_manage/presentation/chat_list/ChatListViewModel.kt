package com.example.msger.feature_chat_manage.presentation.chat_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.core.util.Resource
import com.example.msger.feature_chat_manage.domain.model.Chat
import com.example.msger.feature_authentication.presentation.util.NavigationAuthentication
import com.example.msger.feature_chat_manage.domain.use_case.GetChatsUseCase
import com.example.msger.feature_chat_manage.domain.use_case.SignOutUseCase
import com.example.msger.feature_chat_manage.presentation.util.NavigationChatManage
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
class ChatListViewModel(
    private val signOutUseCase: SignOutUseCase,
    getChatsUseCase: GetChatsUseCase
) : ViewModel() {
    private val _uiState: StateFlow<Resource<List<Chat>>> = getChatsUseCase
        .invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Resource.Loading()
        )
    val uiState: StateFlow<Resource<List<Chat>>>
        get() = _uiState

    fun signOut(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            try {
                signOutUseCase.invoke()
                openAndPopUp(NavigationAuthentication.SignIn.route, NavigationChatManage.ChatList.route)
            } catch (e: Throwable) {
                // TODO: catch errors properly
            }
        }
    }
}