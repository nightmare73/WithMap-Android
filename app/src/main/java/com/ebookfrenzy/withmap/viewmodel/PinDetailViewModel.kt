package com.ebookfrenzy.withmap.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ebookfrenzy.withmap.network.WithMapService
import com.ebookfrenzy.withmap.network.response.PinDetail
import com.ebookfrenzy.withmap.respository.LocalRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

/**
 * Created By Yun Hyeok
 * on 9월 21, 2019
 */

class PinDetailViewModel(
    private val withMapService: WithMapService,
    localRepository: LocalRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val token = localRepository.getAuthToken()

    private val _pinDetail = MutableLiveData<PinDetail>()
    val pinDetail: LiveData<PinDetail>
        get() = _pinDetail

    private val _isRecommended = MutableLiveData<Boolean>()
    val isRecommneded: LiveData<Boolean>
        get() = _isRecommended

    private val _isReported = MutableLiveData<Boolean>()
    val isReported: LiveData<Boolean>
        get() = _isReported

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun getPinDetail(pinId: Int) {
        val token =
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtb2RlbG1ha2VyQG5hdmVyLmNvbSIsIm5pY2tuYW1lIjoic3RyaW5nIiwiaXNzIjoiV0lUSE1BUCIsImlhdCI6MTU2OTU2MzA0NCwiZXhwIjoxNTcwMTY3ODQ0fQ.ZHb2-3h_Ew_pec6t9ta5tCoOLjdZn6xdT7KBtGhao54"
        addDisposable(
            withMapService.getPinDetails(token, pinId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _pinDetail.value = it
                        Log.d("Malibin Debug", "response : $it")
                    },
                    {
                        /* 실패시 코드 작성 */
                        Log.d(
                            "Malibin Debug",
                            "response : ${TextUtils.join("\n", it.stackTrace)}"
                        )
                        Log.d(
                            "Malibin Debug",
                            "errorBody: ${(it as HttpException).response().errorBody()?.string()}"
                        )
                    }
                )

        )
    }

    fun recommendPin(pinId: Int) {
        val token =
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJob21lc2tpbkBuYXZlci5jb20iLCJuaWNrbmFtZSI6Iuy1nOyEoOyerCIsImlzcyI6IldJVEhNQVAiLCJpYXQiOjE1NjkzMjI0NjEsImV4cCI6MTU2OTkyNzI2MX0.c7mUFv1BhyQLwiemXbYYfF_y8tEb45AoOVQ9-btpC_w"
        addDisposable(
            withMapService.recommendPin(token, pinId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { _isRecommended.value = false },
                    {
                        /* 실패시 코드 작성 */
                        _isRecommended.value = true
                        Log.d(
                            "Malibin Debug",
                            "response : ${TextUtils.join("\n", it.stackTrace)}"
                        )
                        Log.d(
                            "Malibin Debug",
                            "response : ${it.message}"
                        )
                    }
                )
        )
    }

    fun reportPin(pinId: Int) {
        val token =
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJob21lc2tpbkBuYXZlci5jb20iLCJuaWNrbmFtZSI6Iuy1nOyEoOyerCIsImlzcyI6IldJVEhNQVAiLCJpYXQiOjE1NjkzMjI0NjEsImV4cCI6MTU2OTkyNzI2MX0.c7mUFv1BhyQLwiemXbYYfF_y8tEb45AoOVQ9-btpC_w"
        addDisposable(
            withMapService.reportPin(token, pinId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { _isReported.value = false },
                    {
                        /* 실패시 코드 작성 */
                        _isReported.value = true
                        Log.d(
                            "Malibin Debug",
                            "response : ${TextUtils.join("\n", it.stackTrace)}"
                        )
                        Log.d(
                            "Malibin Debug",
                            "response : ${it.message}"
                        )
                    }
                )
        )
    }

}