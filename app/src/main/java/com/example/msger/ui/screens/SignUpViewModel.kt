package com.example.msger.ui.screens

import android.util.Log
import androidx.annotation.StringRes
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
import com.example.msger.R
import com.example.msger.common.extensions.emailErrorText
import com.example.msger.common.extensions.isEmailValid
import com.example.msger.common.extensions.isPasswordValid
import com.example.msger.common.extensions.passwordErrorText
import com.example.msger.data.services.AccountService
import com.example.msger.ui.navigation.HOME
import com.example.msger.ui.navigation.SIGN_UP
import com.example.msger.ui.navigation.SIGN_UP_DEBUG_TAG
import kotlinx.coroutines.launch


data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    @StringRes val emailErrorText: Int = R.string.input_required,
    @StringRes val passwordErrorText: Int = R.string.input_required,
    val isLoading: Boolean = false
)

class SignUpViewModel(private val accountService: AccountService) : ViewModel() {
    var uiState: SignUpUiState by mutableStateOf(SignUpUiState())
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

    fun signUp(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isEmailValid = isEmailValid, isPasswordValid = isPasswordValid)

            if (!isEmailValid || !isPasswordValid) {
                return@launch
            }

            uiState = uiState.copy(isLoading = true)
            try {
                accountService.createUserWithEmailAndPassword(email, password)
                openAndPopUp(HOME, SIGN_UP)
            } catch (e: Throwable) {
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

    fun onEmailValueClear() {
        uiState = uiState.copy(
            email = ""
        )
        uiState = uiState.copy(
            isEmailValid = isEmailValid,
            emailErrorText = emailErrorText
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY]) as MsgerApplication
                SignUpViewModel(application.appContainer.accountService)
            }
        }
    }

}