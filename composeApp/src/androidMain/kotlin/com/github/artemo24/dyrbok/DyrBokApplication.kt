package com.github.artemo24.dyrbok

import android.app.Application
import com.github.artemo24.dyrbok.di.initKoin
import org.koin.android.ext.koin.androidContext


class DyrBokApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@DyrBokApplication)
        }
    }
}
