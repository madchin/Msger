package com.example.msger.ui.screens

import android.util.Log
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
import com.example.msger.common.extensions.isEmailValid
import com.example.msger.common.extensions.isPasswordValid
import com.example.msger.data.services.AuthService
import com.example.msger.utils.InputType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "SIGN_UP"

data class HomeUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true
)

class SignUpViewModel(private val authService: AuthService) : ViewModel() {
    var uiState: HomeUiState by mutableStateOf(HomeUiState())
        private set
    private val email: String
        get() = uiState.email
    private val password: String
        get() = uiState.password

    fun signUpAndSignIn() {
        viewModelScope.launch {
            if (!email.isEmailValid()) {
                Log.d(TAG, "Email is not valid")
                uiState = uiState.copy(isEmailValid = false)
                return@launch
            }
            if (!password.isPasswordValid()) {
                Log.d(TAG, "Password is not valid")
                uiState = uiState.copy(isPasswordValid = false)
                return@launch
            }
            withContext(Dispatchers.IO) {
                try {
                    uiState = uiState.copy(isLoading = true)
                    authService.createUserWithEmailAndPassword(email, password)
                    authService.signInWithEmailAndPassword(email, password)
                    uiState = uiState.copy(isLoading = false)

                } catch (e: Throwable) {
                    Log.d("MAIN_ACTIVITY", "Error is: ${e.message}")
                    uiState = uiState.copy(isLoading = false)
                }
            }
        }
    }

    fun onInputChange(input: InputType, value: String) {
        uiState = when (input) {
            InputType.Email -> uiState.copy(email = value, isEmailValid = true)
            InputType.Password -> uiState.copy(password = value, isPasswordValid = true)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY]) as MsgerApplication
                SignUpViewModel(application.appContainer.authService)
            }
        }
    }

}