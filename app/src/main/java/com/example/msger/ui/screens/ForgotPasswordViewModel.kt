package com.example.msger.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.msger.MsgerApplication
import com.example.msger.common.extensions.isEmailValid
import com.example.msger.data.services.AccountService
import com.google.firebase.auth.ActionCodeSettings
import kotlinx.coroutines.launch

private const val TAG = "RESET_PASSWORD"

data class ForgotPasswordUiState(
    val email: String = "",
    val isEmailValid: Boolean = true,
    val isLoading: Boolean = false
)

class ForgotPasswordViewModel(private val accountService: AccountService) : ViewModel() {

    var uiState by mutableStateOf(ForgotPasswordUiState())
        private set

    private val email: String
        get() = uiState.email

    private val isEmailValid: Boolean
        get() = email.isEmailValid()

    fun onEmailChange(value: String) {
        uiState = uiState.copy(email = value, isEmailValid = isEmailValid)
    }

    fun resetPassword() {
        val actionCodeSettings = ActionCodeSettings
            .newBuilder()
            .setUrl("http://msger.page.link/recover-password")
            .setAndroidPackageName("com.example.msger", false,null)
            .build()

        viewModelScope.launch {
            uiState = uiState.copy(isEmailValid = isEmailValid)
            if (!isEmailValid) {
                return@launch
            }
            try {
                accountService.resetPassword(email,actionCodeSettings)
                uiState = ForgotPasswordUiState()
                Log.d(TAG, "Reset password success")
            } catch (e: Throwable) {
                Log.d(TAG, "Reset password failed: $e")
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY] as MsgerApplication)
                ForgotPasswordViewModel(application.appContainer.accountService)
            }
        }
    }
}