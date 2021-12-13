package com.lzitech.filterme.utilities

import android.app.Application
import com.lzitech.filterme.dependencyInjection.repositoryModule
import com.lzitech.filterme.dependencyInjection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@Suppress("unused")
class AppConfig : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppConfig)
            modules(listOf(repositoryModule, viewModelModule))
        }
    }
}