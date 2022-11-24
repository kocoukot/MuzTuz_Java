package com.artline.muztus.ui

import android.app.Application
import com.artline.muztus.BuildConfig
import com.artline.muztus.di.authModule
import com.artline.muztus.di.domainModule
import com.artline.muztus.di.repositoryModule
import com.artline.muztus.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MuztusApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MuztusApplication)
            modules(authModule + repositoryModule + storageModule + domainModule)
        }
    }
}