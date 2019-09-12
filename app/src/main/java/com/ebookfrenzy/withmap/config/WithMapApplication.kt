package com.ebookfrenzy.withmap.config

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created By Yun Hyeok
 * on 9월 12, 2019
 */

class WithMapApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WithMapApplication)
        }
    }
}