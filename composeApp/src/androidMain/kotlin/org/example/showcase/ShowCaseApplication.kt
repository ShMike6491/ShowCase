package org.example.showcase

import android.app.Application
import org.example.showcase.app.di.DependencyInjection
import org.example.showcase.di.androidModule
import org.koin.android.ext.koin.androidContext

class ShowCaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DependencyInjection.initKoin(androidModule) {
            androidContext(this@ShowCaseApplication)
        }
    }
}