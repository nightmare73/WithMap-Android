package com.ebookfrenzy.withmap.viewmodel

import androidx.lifecycle.ViewModel
import com.ebookfrenzy.withmap.respository.LocalRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created By Yun Hyeok
 * on 9월 13, 2019
 */

class SearchViewModel(private val localRepository : LocalRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val isFirst = localRepository.getFirstFlag()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}