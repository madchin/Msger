package com.example.msger.common.extensions

import android.util.Patterns.EMAIL_ADDRESS
import com.example.msger.R

private const val PASSWORD_LENGTH: Int = 12
fun String.isEmailFormatValid(): Boolean = EMAIL_ADDRESS.matcher(this).matches()
fun String.isPasswordLengthValid(): Boolean = this.length >= PASSWORD_LENGTH

fun String.isEmailValid(): Boolean = if (this.isEmpty()) true else when {
        this.isBlank() -> false
        !this.isEmailFormatValid() -> false
        else -> true
    }

fun String.isPasswordValid(): Boolean = if (this.isEmpty()) true else when {
    this.isBlank() -> false
    !this.isPasswordLengthValid() -> false
    else -> true
}

fun String.isConfirmPasswordValid(password: String): Boolean = this.isEmpty() || this == password

fun String.emailErrorText(): Int = if(this.isEmpty()) R.string.input_required else when {
    this.isBlank() -> R.string.input_blank_validation
    !this.isEmailValid() -> R.string.email_input_format_validation
    else -> R.string.input_required
}

fun String.passwordErrorText(): Int = if(this.isEmpty()) R.string.input_required else when {
    this.isBlank() -> R.string.input_blank_validation
    !this.isPasswordValid() -> R.string.password_input_length_validation
    else -> R.string.input_required
}

fun String.confirmPasswordErrorText(password: String): Int =
    if (isConfirmPasswordValid(password = password)) R.string.input_required
    else R.string.confirm_password_validation