package com.example.msger.ui.screens

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
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
import com.example.msger.common.utils.FIREBASE_DYNAMIC_LINK
import com.example.msger.data.services.AccountService
import com.example.msger.ui.navigation.HOME
import com.example.msger.ui.navigation.SIGN_IN
import com.example.msger.ui.navigation.SIGN_IN_DEBUG_TAG
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
    private val accountService: AccountService
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
                val dynamicLink = accountService.getDynamicLink(FIREBASE_DYNAMIC_LINK.toUri())
                val dynamicLinkData = dynamicLink.link
                val userEmail: String = dynamicLinkData?.getQueryParameter("email") ?: ""
                uiState = uiState.copy(email = userEmail)
            } catch (e: Throwable) {
                Log.d(SIGN_IN_DEBUG_TAG, "error occured on sign in: ${e.message}")
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
                responseError = ""
            )

            if (!isEmailValid || !isPasswordValid) {
                return@launch
            }
            uiState = uiState.copy(isLoading = true)

            try {
                accountService.signInWithEmailAndPassword(email, password)
                openAndPopUp(HOME, SIGN_IN)
                Log.d(SIGN_IN_DEBUG_TAG, "USER HAS BEEN SIGNED IN")
            } catch (e: Throwable) {
                uiState = uiState.copy(responseError = e.message.toString())
                Log.d(SIGN_IN_DEBUG_TAG, "ERROR: USER NOT SIGNED IN")
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
                SignInViewModel(application.appContainer.accountService)
            }
        }
    }
}
