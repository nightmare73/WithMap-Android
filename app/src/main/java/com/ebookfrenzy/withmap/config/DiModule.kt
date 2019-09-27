package com.ebookfrenzy.withmap.config

import androidx.lifecycle.ViewModelProvider
import com.ebookfrenzy.withmap.network.KakaoService
import com.ebookfrenzy.withmap.network.WithMapService
import com.ebookfrenzy.withmap.network.response.DataModel
import com.ebookfrenzy.withmap.network.response.DataModelImpl
import com.ebookfrenzy.withmap.respository.LocalRepository
import com.ebookfrenzy.withmap.respository.SharedPreferenceSource
import com.ebookfrenzy.withmap.viewmodel.MainViewModel
import com.ebookfrenzy.withmap.viewmodel.PinDetailViewModel
import com.ebookfrenzy.withmap.viewmodel.SearchViewModel
import com.ebookfrenzy.withmap.viewmodel.ViewModelFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created By Yun Hyeok
 * on 9월 12, 2019
 */

val kakaoApiModule = module(override = true) {
    single<OkHttpClient>(override = true) {
        OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("Authorization", "KakaoAK aecd6253cfe21f9862b4815c74a48857")
                    .build()
                return@addInterceptor it.proceed(request)
            }
            .build()
    }

    single<KakaoService> {
        Retrofit.Builder()
            .baseUrl(KakaoService.baseUrl)
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KakaoService::class.java)
    }
}

val apiModule = module(override = true) {
    single<OkHttpClient>(override = true) {
        OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
                return@addInterceptor it.proceed(request)
            }
            .build()
    }

    single<WithMapService> {
        Retrofit.Builder()
            .baseUrl(WithMapService.baseUrl)
            .client(get())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WithMapService::class.java)
    }
}

val localRepositoryModule = module {
    single { SharedPreferenceSource(androidContext()) }
    single { LocalRepository(get()) }
}

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { PinDetailViewModel(get(), get()) }
}

val viewModelFactoryModule = module {
    single<ViewModelProvider.Factory> { ViewModelFactory(get(), get(), get()) }
}

val mainViewModelModule = module {
    viewModel{MainViewModel()}
}

val modelpart = module {
    factory<DataModel> {
        DataModelImpl(get())
    }
}

val diModules =
    listOf(
        apiModule,
        kakaoApiModule,
        viewModelModule,
        localRepositoryModule,
        modelpart,
        mainViewModelModule,
        viewModelFactoryModule
    )