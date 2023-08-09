package com.example.msger.feature_authentication.presentation.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.msger.core.presentation.navigation.NavigationRoute
import com.example.msger.feature_authentication.domain.use_case.GetEmailFromDeepLinkUseCase
import com.example.msger.feature_authentication.domain.use_case.SignInUseCase
import com.example.msger.feature_authentication.presentation.util.isEmailValid
import com.example.msger.feature_authentication.presentation.util.isPasswordValid
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignInViewModel(
    private val getEmailFromDeepLinkUseCase: GetEmailFromDeepLinkUseCase,
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    var email: String by mutableStateOf("")
        private set
    var password: String by mutableStateOf("")
        private set
    var isEmailValid: Boolean by mutableStateOf(true)
        private set
    var isPasswordValid: Boolean by mutableStateOf(true)
        private set
    var isLoading: Boolean by mutableStateOf(false)
        private set
    var responseError: String by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            try {
                val userEmail: String = getEmailFromDeepLinkUseCase() ?: ""
                email = userEmail
            } catch (e: Throwable) {
                // TODO: catch errors properly
            }
        }
    }

    fun signInUser(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch {
            isEmailValid = isEmailValid(email = email)
            isPasswordValid = isPasswordValid(password = password)

            if (!isEmailValid || !isPasswordValid) {
                return@launch
            }
            isLoading = true

            try {
                signInUseCase(email, password)
                isLoading = false
                email = ""
                password = ""
                openAndPopUp(NavigationRoute.ChatList.route, NavigationRoute.SignIn.route)
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

    fun onEmailValueClear() {
        email = ""
        isEmailValid = isEmailValid(email = email)
    }
}
