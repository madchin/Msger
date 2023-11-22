package com.example.msger.core.util

import com.example.msger.core.util.exception.GenericException
import com.google.gson.Gson

fun parseErrorBody(body: String): ErrorResponse = Gson().fromJson(body, ErrorResponse::class.java)
data class ErrorResponse(
    val status: Int = 400,
    val message: String = GenericException().message
)

sealed class ApiResponse<T>(
    status: Int? = null,
    data: T? = null,
    errorResponse: ErrorResponse? = null
) {
    class Success<T>(val status: Int, val data: T) : ApiResponse<T>(status = status, data = data)

    class Error<T>(val status: Int? = 400, val errorResponse: ErrorResponse? = ErrorResponse()) :
        ApiResponse<T>(status = status, errorResponse = errorResponse)

}