package com.example.msger.common.extensions

import android.util.Patterns.EMAIL_ADDRESS
import com.example.msger.R
import com.example.msger.ui.NavigationRoute

private const val PASSWORD_LENGTH: Int = 12
fun String.isEmailFormatValid(): Boolean = EMAIL_ADDRESS.matcher(this.trim()).matches()
fun String.isPasswordLengthValid(): Boolean = this.length >= PASSWORD_LENGTH

fun String.isEmailValid(): Boolean =  when {
        this.isBlank() -> false
        !this.isEmailFormatValid() -> false
        else -> true
    }

fun String.isPasswordValid(): Boolean = when {
    this.isBlank() -> false
    !this.isPasswordLengthValid() -> false
    else -> true
}

fun String.isConfirmPasswordValid(password: String): Boolean = this == password

fun String.emailErrorText(): Int = when {
    this.isBlank() -> R.string.input_blank_validation
    !this.isEmailValid() -> R.string.email_input_format_validation
    else -> R.string.input_required
}

fun String.passwordErrorText(): Int = when {
    this.isBlank() -> R.string.input_blank_validation
    !this.isPasswordValid() -> R.string.password_input_length_validation
    else -> R.string.input_required
}

fun String.confirmPasswordErrorText(password: String): Int =
    if (isConfirmPasswordValid(password = password)) R.string.input_required
    else R.string.confirm_password_validation

fun String.toNavigationRoute(): NavigationRoute? = when(this) {
    NavigationRoute.Home.route -> NavigationRoute.Home
    NavigationRoute.SignIn.route -> NavigationRoute.SignIn
    NavigationRoute.SplashScreen.route -> NavigationRoute.SplashScreen
    NavigationRoute.ForgotPassword.route -> NavigationRoute.ForgotPassword
    NavigationRoute.SignUp.route -> NavigationRoute.SignUp
    else -> null
}