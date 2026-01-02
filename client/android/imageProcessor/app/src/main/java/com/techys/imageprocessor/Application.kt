package com.techys.imageprocessor

import android.app.Application
import com.techys.core.di.AppContainer
import com.techys.imageprocessor.di.AppContainerImpl
import timber.log.Timber

class Application: Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        appContainer = AppContainerImpl
    }
}