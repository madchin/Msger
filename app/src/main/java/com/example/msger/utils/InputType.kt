package com.example.msger.utils


sealed interface InputType {
    object Email : InputType
    object Password : InputType
}