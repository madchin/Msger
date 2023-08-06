package com.example.msger.feature_authentication.presentation.util

import com.example.msger.R

fun passwordInputSupportText(password: String): Int = when {
    password.isBlank() -> R.string.input_blank_validation
    !isPasswordLengthValid(password = password) -> R.string.password_input_length_validation
    else -> R.string.input_required
}

fun confirmPasswordInputSupportText(password: String, confirmPassword: String): Int =
    if (isConfirmPasswordValid(password = password, confirmPassword = confirmPassword)) R.string.input_required
    else R.string.confirm_password_validation

fun emailInputSupportText(email: String): Int = when {
    email.isBlank() -> R.string.input_blank_validation
    !isEmailFormatValid(email = email) -> R.string.email_input_format_validation
    else -> R.string.input_required
}