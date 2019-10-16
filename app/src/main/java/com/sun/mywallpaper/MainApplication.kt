package com.sun.mywallpaper

import android.app.Application
import com.sun.mywallpaper.di.apiModule
import com.sun.mywallpaper.di.componentModule
import com.sun.mywallpaper.di.sourceModule
import com.sun.mywallpaper.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(listOf(apiModule, sourceModule, componentModule, viewModelModule))
        }
    }
}
