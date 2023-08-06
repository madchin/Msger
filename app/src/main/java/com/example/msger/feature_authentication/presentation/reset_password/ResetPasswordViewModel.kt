package com.example.msger.feature_authentication.presentation.reset_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.feature_authentication.domain.use_case.ResetPasswordUseCase
import com.example.msger.feature_authentication.presentation.util.isEmailValid
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {
    var email: String by mutableStateOf("")
        private set

    var isEmailValid: Boolean by mutableStateOf(true)
        private set

    var isLoading: Boolean by mutableStateOf(false)
        private set

    var responseError: String by mutableStateOf("")
        private set

    fun onEmailValueChange(value: String) {
        email = value
        isEmailValid = isEmailValid(email = email)
    }

    fun onEmailValueClear() {
        email = ""
        isEmailValid = true
    }

    fun resetPassword() {
        viewModelScope.launch {
            isEmailValid = isEmailValid(email = email)
            if (!isEmailValid) {
                return@launch
            }
            isLoading = true
            try {
                resetPasswordUseCase(email = email)
                email = ""
                isEmailValid = true
            } catch (e: Throwable) {
                isLoading = false
                responseError = e.message.toString()
                delay(5000L)
                responseError = ""
            }
        }
    }
}