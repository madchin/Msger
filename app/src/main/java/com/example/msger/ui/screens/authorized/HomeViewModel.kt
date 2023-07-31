package com.example.msger.ui.screens.authorized

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.data.model.Chat
import com.example.msger.data.services.auth.AuthService
import com.example.msger.data.services.db.DbService
import com.example.msger.ui.NavigationRoute
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


sealed interface HomeUiState {
    data class Success(val chats: List<Chat> = listOf(Chat())) : HomeUiState
    data class Failure(val error: Throwable?) : HomeUiState

    object Loading : HomeUiState
}

class HomeViewModel(
    private val authService: AuthService,
    private val dbService: DbService
) : ViewModel() {
    private val _chats = dbService.chats.map {
        when {
            it.isSuccess -> HomeUiState.Success(it.getOrDefault(listOf(Chat())))
            it.isFailure -> HomeUiState.Failure(it.exceptionOrNull() ?: Exception("generic"))
            else -> HomeUiState.Loading
        }
    }
    val chats
        get() = _chats

    init {
        viewModelScope.launch {
            try {
                dbService.createChat()
            } catch (e: Throwable) {
                Log.d("HOME", e.message.toString())
            }
        }
    }

    fun signOut(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            try {
                authService.signOut()
                openAndPopUp(NavigationRoute.SignIn.route, NavigationRoute.Home.route)
            } catch (e: Throwable) {
                // TODO: catch errors properly
            }
        }
    }
}