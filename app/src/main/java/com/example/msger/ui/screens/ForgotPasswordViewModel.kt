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
import com.example.msger.R
import com.example.msger.androidPackageName
import com.example.msger.common.extensions.emailErrorText
import com.example.msger.common.extensions.isEmailValid
import com.example.msger.common.utils.DEEP_LINK_HOST
import com.example.msger.common.utils.DEEP_LINK_SCHEME
import com.example.msger.data.services.AccountService
import com.example.msger.ui.navigation.FORGOT_PASSWORD_DEBUG_TAG
import com.google.firebase.auth.ActionCodeSettings
import kotlinx.coroutines.launch


data class ForgotPasswordUiState(
    val email: String = "",
    val isEmailValid: Boolean = true,
    val emailErrorText: Int = R.string.input_required,
    val isLoading: Boolean = false
)

class ForgotPasswordViewModel(private val accountService: AccountService) : ViewModel() {

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
            try {
                val url = "$DEEP_LINK_SCHEME$DEEP_LINK_HOST/change-password?email=$email"
                val actionCodeSettings = ActionCodeSettings
                    .newBuilder()
                    .setUrl(url)
                    .setAndroidPackageName(androidPackageName, false, null)
                    .build()
                accountService.resetPassword(email, actionCodeSettings)
                uiState = ForgotPasswordUiState()
                Log.d(FORGOT_PASSWORD_DEBUG_TAG, "Reset password success")
            } catch (e: Throwable) {
                Log.d(FORGOT_PASSWORD_DEBUG_TAG, "Reset password failed: $e")
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