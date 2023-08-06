package com.example.msger.core.data

import android.app.Application
import com.example.msger.feature_authentication.data.AppContainer
    lateinit var androidPackageName: String
class MsgerApplication : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer()
        androidPackageName = packageName
    }
}