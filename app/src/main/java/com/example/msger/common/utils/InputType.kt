package com.example.msger.common.utils


sealed interface InputType {
    object Email : InputType
    object Password : InputType
}