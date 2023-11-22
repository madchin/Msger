package com.example.msger.feature_authentication.presentation.util

import com.example.msger.R

fun passwordInputSupportText(password: String): Int = when {
    password.isNotEmpty() && password.isBlank() -> R.string.input_blank_validation
    password.isNotEmpty() && !isPasswordLengthValid(password = password) -> R.string.password_input_length_validation
    else -> R.string.input_required
}

fun usernameInputSupportText(username: String) = when {
    username.isNotEmpty() && username.isBlank() -> R.string.input_blank_validation
    username.isNotEmpty() && !isUsernameLengthValid(username) -> R.string.username_input_length_validation
    else -> R.string.input_required
}

fun confirmPasswordInputSupportText(password: String, confirmPassword: String): Int =
    if (isConfirmPasswordValid(password = password, confirmPassword = confirmPassword)) R.string.input_required
    else R.string.confirm_password_validation

fun emailInputSupportText(email: String): Int = when {
    email.isNotEmpty() && email.isBlank() -> R.string.input_blank_validation
    email.isNotEmpty() && !isEmailFormatValid(email = email) -> R.string.email_input_format_validation
    else -> R.string.input_required
}