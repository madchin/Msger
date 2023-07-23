package com.example.msger.common.extensions

import android.util.Patterns.EMAIL_ADDRESS
import com.example.msger.R

private const val PASSWORD_LENGTH: Int = 12
fun String.isEmailFormatValid(): Boolean = EMAIL_ADDRESS.matcher(this).matches()
fun String.isPasswordLengthValid(): Boolean = this.length >= PASSWORD_LENGTH

fun String.isEmailValid(): Boolean = when {
    this.isBlank() && this.isNotEmpty() -> false
    !this.isEmailFormatValid() && this.isNotEmpty() -> false
    else -> true
}

fun String.isPasswordValid(): Boolean = when {
    this.isBlank() && this.isNotEmpty() -> false
    !this.isPasswordLengthValid() && this.isNotEmpty() -> false
    else -> true
}


fun String.emailErrorText(): Int = when {
    this.isBlank() && this.isNotEmpty() -> R.string.input_blank_validation
    !this.isEmailValid() && this.isNotEmpty() -> R.string.email_input_format_validation
    else -> R.string.input_required
}

fun String.passwordErrorText(): Int = when {
    this.isBlank() && this.isNotEmpty() -> R.string.input_blank_validation
    !this.isPasswordValid() && this.isNotEmpty() -> R.string.password_input_length_validation
    else -> R.string.input_required
}