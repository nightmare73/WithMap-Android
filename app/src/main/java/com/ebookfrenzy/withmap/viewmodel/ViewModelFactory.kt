package com.ebookfrenzy.withmap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ebookfrenzy.withmap.network.KakaoService
import com.ebookfrenzy.withmap.network.WithMapService
import com.ebookfrenzy.withmap.respository.LocalRepository

/**
 * Created By Yun Hyeok
 * on 9월 27, 2019
 */

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val withMapService: WithMapService,
    private val kakaoService: KakaoService,
    private val localRepository: LocalRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            SearchViewModel::class.java -> {
                SearchViewModel(kakaoService) as T
            }
            PinDetailViewModel::class.java -> {
                PinDetailViewModel(withMapService, localRepository) as T
            }
            else -> modelClass.getConstructor(WithMapService::class.java)
                .newInstance(withMapService)
        }
    }
}