package com.example.msger.feature_authentication.presentation.util

import android.util.Patterns.EMAIL_ADDRESS

private const val PASSWORD_LENGTH: Int = 12
fun isEmailFormatValid(email: String): Boolean = EMAIL_ADDRESS.matcher(email.trim()).matches()
fun isPasswordLengthValid(password: String): Boolean = password.length >= PASSWORD_LENGTH

fun isEmailValid(email: String): Boolean = when {
    email.isBlank() -> false
    !isEmailFormatValid(email) -> false
    else -> true
}

fun isPasswordValid(password: String): Boolean = when {
    password.isBlank() -> false
    !isPasswordLengthValid(password) -> false
    else -> true
}

fun isConfirmPasswordValid(password: String, confirmPassword: String): Boolean = confirmPassword == password