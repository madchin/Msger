package com.example.msger

import android.app.Application
import com.example.msger.data.AppContainer
    lateinit var androidPackageName: String
class MsgerApplication : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer()
        androidPackageName = packageName
    }
}