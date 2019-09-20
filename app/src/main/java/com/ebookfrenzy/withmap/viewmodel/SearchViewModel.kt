package com.ebookfrenzy.withmap.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ebookfrenzy.withmap.network.KakaoService
import com.ebookfrenzy.withmap.network.response.KeywordAddressDocument
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created By Yun Hyeok
 * on 9월 13, 2019
 */

class SearchViewModel(private val kakaoService: KakaoService) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _searchResult = MutableLiveData<List<KeywordAddressDocument>>()
    val searchResult: LiveData<List<KeywordAddressDocument>>
        get() = _searchResult

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun getSearchResult(query: String) {
        addDisposable(
            kakaoService.requestKakaoKeywordSearch(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { _searchResult.value = it.documents },
                    { /* 실패시 코드 작성 */ }
                )
        )
    }
}