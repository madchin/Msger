package com.example.msger.core.util.config

import com.example.msger.core.util.ApiResponse
import com.example.msger.core.util.parseErrorBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun <T> Response<T>.toApiResponse(): ApiResponse<T> {
    val responseBody = body()
    return when {
        isSuccessful && responseBody != null -> ApiResponse.Success(
            status = code(),
            data = responseBody
        )

        else -> ApiResponse.Error(status = code(), errorResponse = parseErrorBody(message()))
    }
}

object RetrofitConfig {
    private val scheme = "http"
    private val host = "10.0.2.2" // android emulator loopback ip
    private val port = "8080"
    private val baseUrl = "$scheme://$host:$port/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(service: Class<T>) = retrofit.create(service)
}