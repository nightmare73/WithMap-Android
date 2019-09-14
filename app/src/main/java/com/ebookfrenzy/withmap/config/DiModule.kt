package com.ebookfrenzy.withmap.config

import com.ebookfrenzy.withmap.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created By Yun Hyeok
 * on 9월 12, 2019
 */


val apiModule = module {

}

val viewModelModule = module {
    viewModel {
        SearchViewModel()
    }
}

val diModules = listOf(apiModule, viewModelModule)