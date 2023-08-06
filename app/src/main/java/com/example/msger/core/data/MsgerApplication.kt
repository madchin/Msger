package com.example.msger.core.data

import android.app.Application

lateinit var androidPackageName: String
class MsgerApplication : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer()
        androidPackageName = packageName
    }
}