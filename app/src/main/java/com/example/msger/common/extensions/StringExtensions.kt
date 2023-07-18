package com.example.msger.common.extensions

import android.util.Patterns.EMAIL_ADDRESS

private const val PASSWORD_LENGTH: Int = 12

fun String.isEmailValid(): Boolean = EMAIL_ADDRESS.matcher(this).matches()


fun String.isPasswordValid(): Boolean = this.isNotBlank() && this.length >= PASSWORD_LENGTH