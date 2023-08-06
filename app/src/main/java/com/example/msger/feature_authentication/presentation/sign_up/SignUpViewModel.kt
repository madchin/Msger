package com.example.msger.feature_authentication.presentation.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.core.presentation.navigation.NavigationRoute
import com.example.msger.feature_authentication.domain.use_case.SignUpUseCase
import com.example.msger.feature_authentication.presentation.util.isConfirmPasswordValid
import com.example.msger.feature_authentication.presentation.util.isEmailValid
import com.example.msger.feature_authentication.presentation.util.isPasswordValid
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    var email: String by mutableStateOf("")
        private set

    var password: String by mutableStateOf("")
        private set

    var confirmPassword: String by mutableStateOf("")
        private set

    var isEmailValid: Boolean by mutableStateOf(true)
        private set

    var isPasswordValid: Boolean by mutableStateOf(true)
        private set

    var isConfirmPasswordValid: Boolean by mutableStateOf(true)
        private set

    var isLoading: Boolean by mutableStateOf(false)
        private set
    var responseError: String by mutableStateOf("")
        private set

    fun signUpUser(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {

            isEmailValid = isEmailValid(email = email)
            isPasswordValid = isPasswordValid(password = password)
            isConfirmPasswordValid =
                isConfirmPasswordValid(password = password, confirmPassword = confirmPassword)

            if (!isEmailValid || !isPasswordValid || !isConfirmPasswordValid) {
                return@launch
            }

            isLoading = true
            try {
                signUpUseCase(email = email, password = password)
                isLoading = false
                openAndPopUp(NavigationRoute.ChatList.route, NavigationRoute.SignUp.route)
            } catch (e: Throwable) {
                isLoading = false
                responseError = e.message.toString()
                delay(5000L)
                responseError = ""
            }
        }
    }

    fun onEmailValueChange(value: String) {
        email = value
        isEmailValid = isEmailValid(email = email)
    }

    fun onPasswordValueChange(value: String) {
        password = value
        isPasswordValid = isPasswordValid(password = password)
    }

    fun onConfirmPasswordValueChange(value: String) {
        confirmPassword = value
        isConfirmPasswordValid =
            isConfirmPasswordValid(password = password, confirmPassword = confirmPassword)
    }

    fun onEmailValueClear() {
        email = ""
        isEmailValid = isEmailValid(email = email)
    }
}