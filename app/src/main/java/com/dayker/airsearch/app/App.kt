package com.dayker.airsearch.app

import android.app.Application
import com.dayker.airsearch.di.appModule
import com.dayker.airsearch.di.modelModule
import com.dayker.airsearch.di.presenterModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(presenterModule, modelModule, appModule))
        }
    }
}