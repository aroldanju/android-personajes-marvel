package com.aroldan.marvelapp

import android.app.Application
import com.aroldan.marvelapp.common.di.data.apiModule
import com.aroldan.marvelapp.common.di.data.networkModule
import com.aroldan.marvelapp.common.di.data.repositoryModule
import com.aroldan.marvelapp.common.di.domain.useCaseModule
import com.aroldan.marvelapp.common.di.presentation.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@App)
            modules(listOf(
                    networkModule,
                    apiModule,
                    repositoryModule,
                    viewModule,
                    useCaseModule
            ))
        }
    }

}