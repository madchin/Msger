package com.example.msger.ui.screens

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.R
import com.example.msger.common.extensions.confirmPasswordErrorText
import com.example.msger.common.extensions.emailErrorText
import com.example.msger.common.extensions.isConfirmPasswordValid
import com.example.msger.common.extensions.isEmailValid
import com.example.msger.common.extensions.isPasswordValid
import com.example.msger.common.extensions.passwordErrorText
import com.example.msger.data.services.AccountService
import com.example.msger.ui.HOME
import com.example.msger.ui.SIGN_UP
import com.example.msger.ui.SIGN_UP_DEBUG_TAG
import kotlinx.coroutines.launch


data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isConfirmPasswordValid: Boolean = true,
    @StringRes val emailErrorText: Int = R.string.input_required,
    @StringRes val passwordErrorText: Int = R.string.input_required,
    @StringRes val confirmPasswordErrorText: Int = R.string.input_required,
    val isLoading: Boolean = false,
    val responseError: String = ""
)

class SignUpViewModel(private val accountService: AccountService) : ViewModel() {
    var uiState: SignUpUiState by mutableStateOf(SignUpUiState())
        private set
    private val email: String
        get() = uiState.email

    private val password: String
        get() = uiState.password

    private val confirmPassword: String
        get() = uiState.confirmPassword
    private val emailErrorText: Int
        get() = email.emailErrorText()
    private val passwordErrorText: Int
        get() = password.passwordErrorText()

    private val confirmPasswordErrorText: Int
        get() = confirmPassword.confirmPasswordErrorText(password = password)
    private val isEmailValid: Boolean
        get() = email.isEmailValid()

    private val isPasswordValid: Boolean
        get() = password.isPasswordValid()

    private val isConfirmPasswordValid: Boolean
        get() = confirmPassword.isConfirmPasswordValid(password = password)

    fun signUp(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(
                isEmailValid = isEmailValid,
                isPasswordValid = isPasswordValid,
                isConfirmPasswordValid = isConfirmPasswordValid,
                responseError = ""
            )

            if (!isEmailValid || !isPasswordValid || !isConfirmPasswordValid) {
                return@launch
            }

            uiState = uiState.copy(isLoading = true)
            try {
                accountService.createUserWithEmailAndPassword(email, password)
                openAndPopUp(HOME, SIGN_UP)
            } catch (e: Throwable) {
                uiState = uiState.copy(responseError = e.message.toString())
                Log.d(SIGN_UP_DEBUG_TAG, "Error is: ${e.message}")
            }
            uiState = uiState.copy(isLoading = false)
        }
    }


    fun onEmailValueChange(value: String) {
        uiState = uiState.copy(email = value)
        uiState = uiState.copy(
            isEmailValid = isEmailValid,
            emailErrorText = emailErrorText
        )
    }

    fun onPasswordValueChange(value: String) {
        uiState = uiState.copy(password = value)
        uiState = uiState.copy(
            isPasswordValid = isPasswordValid,
            passwordErrorText = passwordErrorText
        )
    }

    fun onConfirmPasswordValueChange(value: String) {
        uiState = uiState.copy(confirmPassword = value)
        uiState = uiState.copy(
            isConfirmPasswordValid = isConfirmPasswordValid,
            confirmPasswordErrorText = confirmPasswordErrorText
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
}