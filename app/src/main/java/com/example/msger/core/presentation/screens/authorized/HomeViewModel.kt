package com.example.msger.core.presentation.screens.authorized

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.core.util.Resource
import com.example.msger.data.model.db.ChatEntity
import com.example.msger.feature_authentication.domain.repository.AuthRepository
import com.example.msger.data.services.db.DbService
import com.example.msger.core.presentation.navigation.NavigationRoute
import com.example.msger.feature_authentication.presentation.util.NavigationAuthentication
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


data class HomeUiState(val chats: List<ChatEntity>)
class HomeViewModel(
    private val authRepository: AuthRepository,
    dbService: DbService
) : ViewModel() {
    private val _uiState: StateFlow<Resource<HomeUiState>> = dbService
        .chats
        .map {
            when {
                it.isSuccess -> Resource.Success(HomeUiState(it.getOrDefault(listOf())))
                it.isFailure -> {
                    val error = it.exceptionOrNull()
                    Resource.Error(error?.message.toString())
                }

                else -> Resource.Loading()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Resource.Loading()
        )
    val uiState: StateFlow<Resource<HomeUiState>>
        get() = _uiState

    fun signOut(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            try {
                authRepository.signOut()
                openAndPopUp(NavigationAuthentication.SignIn.route, NavigationRoute.Home.route)
            } catch (e: Throwable) {
                // TODO: catch errors properly
            }
        }
    }
}