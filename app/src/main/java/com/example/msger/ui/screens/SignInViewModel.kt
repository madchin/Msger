package com.example.msger.ui.screens

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.R
import com.example.msger.common.extensions.emailErrorText
import com.example.msger.common.extensions.isEmailValid
import com.example.msger.common.extensions.isPasswordValid
import com.example.msger.common.extensions.passwordErrorText
import com.example.msger.data.services.auth.AuthService
import com.example.msger.ui.NavigationRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    @StringRes val emailErrorText: Int = R.string.input_required,
    @StringRes val passwordErrorText: Int = R.string.input_required,
    val isLoading: Boolean = false,
    val responseError: String = ""
)

class SignInViewModel(
    private val authService: AuthService
) : ViewModel() {
    var uiState: SignInUiState by mutableStateOf(SignInUiState())
        private set

    private val email: String
        get() = uiState.email

    private val password: String
        get() = uiState.password
    private val emailErrorText: Int
        get() = email.emailErrorText()
    private val passwordErrorText: Int
        get() = password.passwordErrorText()
    private val isEmailValid: Boolean
        get() = email.isEmailValid()

    private val isPasswordValid: Boolean
        get() = password.isPasswordValid()

    init {
        viewModelScope.launch {
            try {
                val email: String = authService.getUserEmail()
                uiState = uiState.copy(email = email)
            } catch (e: Throwable) {
                // TODO: catch errors properly
            }
        }
    }

    fun signInWithEmailAndPassword(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(
                isEmailValid = isEmailValid,
                isPasswordValid = isPasswordValid,
                passwordErrorText = passwordErrorText,
                emailErrorText = emailErrorText,
            )

            if (!isEmailValid || !isPasswordValid) {
                return@launch
            }
            uiState = uiState.copy(isLoading = true)

            try {
                authService.signInWithEmailAndPassword(email, password)
                uiState = uiState.copy(isLoading = false)
                openAndPopUp(NavigationRoute.Home.route, NavigationRoute.SignIn.route)
            } catch (e: Throwable) {
                uiState = uiState.copy(isLoading = false, responseError = e.message.toString())
                delay(5000L)
                uiState = uiState.copy(responseError = "")
            }
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
