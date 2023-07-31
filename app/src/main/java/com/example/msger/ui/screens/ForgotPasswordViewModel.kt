package com.example.msger.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.R
import com.example.msger.common.extensions.emailErrorText
import com.example.msger.common.extensions.isEmailValid
import com.example.msger.data.services.auth.AuthService
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


data class ForgotPasswordUiState(
    val email: String = "",
    val isEmailValid: Boolean = true,
    val emailErrorText: Int = R.string.input_required,
    val isLoading: Boolean = false,
    val responseError: String = ""
)

class ForgotPasswordViewModel(private val authService: AuthService) : ViewModel() {

    var uiState: ForgotPasswordUiState by mutableStateOf(ForgotPasswordUiState())
        private set

    private val email: String
        get() = uiState.email

    private val isEmailValid: Boolean
        get() = email.isEmailValid()
    private val emailErrorText: Int
        get() = email.emailErrorText()

    fun onEmailValueChange(value: String) {
        uiState = uiState.copy(email = value)
        uiState = uiState.copy(
            isEmailValid = isEmailValid,
            emailErrorText = emailErrorText
        )
    }

    fun onEmailValueClear() {
        uiState = uiState.copy(
            email = ""
        )
        uiState = uiState.copy(
            isEmailValid = isEmailValid,
            emailErrorText = emailErrorText
        )
    }

    fun resetPassword() {
        viewModelScope.launch {
            uiState = uiState.copy(isEmailValid = isEmailValid)
            if (!isEmailValid) {
                return@launch
            }
            uiState = uiState.copy(isLoading = true)
            try {
                authService.resetPassword(email)
                uiState = ForgotPasswordUiState()
            } catch (e: Throwable) {
                uiState = uiState.copy(isLoading = false, responseError = e.message.toString())
                delay(5000L)
                uiState = uiState.copy(responseError = "")
            }
        }
    }
}