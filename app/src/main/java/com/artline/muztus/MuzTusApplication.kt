package com.artline.muztus

import android.app.Application
import com.artline.muztus.di.authModule
import com.artline.muztus.di.repositoryModule
import com.artline.muztus.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MuzTusApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MuzTusApplication)
            modules(authModule + repositoryModule + storageModule)
        }
    }
}