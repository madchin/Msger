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
import com.example.msger.ui.navigation.SIGN_UP
import com.example.msger.common.utils.InputType
import kotlinx.coroutines.launch


data class HomeUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true
)

class SignUpViewModel(private val accountService: AccountService) : ViewModel() {
    var uiState: HomeUiState by mutableStateOf(HomeUiState())
        private set
    private val email: String
        get() = uiState.email
    private val password: String
        get() = uiState.password

    private val isEmailValid: Boolean
        get() = email.isEmailValid()
    private val isPasswordValid: Boolean
        get() = password.isPasswordValid()

    fun signUp(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isEmailValid = isEmailValid, isPasswordValid = isPasswordValid)

            if(!isEmailValid || !isPasswordValid) {
                return@launch
            }

            uiState = uiState.copy(isLoading = true)
            try {
                accountService.createUserWithEmailAndPassword(email, password)
                openAndPopUp(HOME, SIGN_UP)
            } catch (e: Throwable) {
                Log.d("MAIN_ACTIVITY", "Error is: ${e.message}")
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
                SignUpViewModel(application.appContainer.accountService)
            }
        }
    }

}