package com.example.msger.core.util.config

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitConfig {
    private val scheme = "http"
    private val host = "10.0.2.2"
    private val port = "8080"
    private val baseUrl = "$scheme://$host:$port/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun <T> create(service: Class<T>) = retrofit.create(service)
}