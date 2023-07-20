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
import com.example.msger.data.services.AccountService
import com.example.msger.ui.navigation.HOME
import com.example.msger.ui.navigation.SIGN_IN
import com.example.msger.utils.InputType
import kotlinx.coroutines.launch

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isLoading: Boolean = false
)

private const val TAG = "SIGN_IN"

class SignInViewModel(
    private val accountService: AccountService
) : ViewModel() {
    var uiState by mutableStateOf(SignInUiState())
        private set
    private val email
        get() = uiState.email

    private val password
        get() = uiState.password

    private val isEmailValid: Boolean
        get() = email.isEmailValid()
    private val isPasswordValid: Boolean
        get() = password.isPasswordValid()

    fun signInWithEmailAndPassword(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isEmailValid = isEmailValid, isPasswordValid = isPasswordValid)

            if (!isEmailValid || !isPasswordValid) {
                return@launch
            }
            uiState = uiState.copy(isLoading = true)

            try {
                accountService.signInWithEmailAndPassword(email, password)
                openAndPopUp(HOME, SIGN_IN)
                Log.d(TAG, "USER HAS BEEN SIGNED IN")
            } catch (e: Throwable) {
                Log.d(TAG, "ERROR: USER NOT SIGNED IN")
            }
            uiState = uiState.copy(isLoading = false)
        }
    }

    fun onInputChange(input: InputType, value: String) {
        uiState = when (input) {
            InputType.Email -> uiState.copy(email = value, isEmailValid = isEmailValid)
            InputType.Password -> uiState.copy(password = value, isPasswordValid = isPasswordValid)
        }
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
