package com.example.msger.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.msger.MsgerApplication
import com.example.msger.data.services.AuthService
import com.example.msger.utils.InputType
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isError: String = "",
    val isSuccess: FirebaseUser? = null
)


class LoginViewModel(
    private val authService: AuthService
) : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun signInWithEmailAndPassword() {
        viewModelScope.launch(Dispatchers.IO) {
            uiState = uiState.copy(isLoading = true)
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
                LoginViewModel(application.appContainer.authService)
            }
        }
    }
}
