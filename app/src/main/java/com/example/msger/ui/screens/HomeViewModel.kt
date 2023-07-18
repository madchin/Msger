package com.example.msger.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.msger.MsgerApplication
import com.example.msger.data.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.example.msger.utils.InputType
import java.lang.Exception

data class HomeUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isError: Boolean = false,
    val isSignUpSuccess: Boolean = false
)

class HomeViewModel(private val authService: AuthService) : ViewModel() {
    var uiState: HomeUiState by mutableStateOf(HomeUiState())
        private set


    fun signUp() {
        viewModelScope.launch(Dispatchers.IO) {
            uiState = uiState.copy(isLoading = true, isError = false, isSignUpSuccess = false)
            authService.createUserWithEmailAndPassword(uiState.email, uiState.password) {
                when {
                    it.isSuccessful -> uiState = HomeUiState(isSignUpSuccess = true, isLoading = false)
                    it.isCanceled -> uiState = HomeUiState(isError = true, isLoading = false)
                    it.exception is Exception -> uiState = HomeUiState(isError = true, isLoading = false)
                }
            }
        }
    }

    fun onInputChange(input: InputType, value: String) {
        uiState = when (input) {
            InputType.Email -> uiState.copy(email = value)
            InputType.Password -> uiState.copy(password = value)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
            val application = checkNotNull(this[APPLICATION_KEY]) as MsgerApplication
                HomeViewModel(application.appContainer.authService)
            }
        }
    }

}