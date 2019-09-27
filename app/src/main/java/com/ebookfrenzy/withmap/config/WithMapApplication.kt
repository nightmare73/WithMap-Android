package com.ebookfrenzy.withmap.config

import android.app.Application
import com.ebookfrenzy.withmap.network.WithMapService
import com.ebookfrenzy.withmap.network.WithMapService.Companion.baseUrl
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created By Yun Hyeok
 * on 9월 12, 2019
 */

class WithMapApplication : Application() {
    lateinit var networkService : WithMapService
    companion object {
        lateinit var instance : WithMapApplication
    }

    fun buildNetwork() {
        val builder = Retrofit.Builder()
        val retrofit = builder
            .baseUrl("http://withmap-253307.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        networkService = retrofit.create(WithMapService::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        buildNetwork()

        startKoin {
            androidContext(this@WithMapApplication)
            modules(diModules)
        }
    }


}