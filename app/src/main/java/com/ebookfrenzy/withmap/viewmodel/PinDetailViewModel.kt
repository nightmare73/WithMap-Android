package com.ebookfrenzy.withmap.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ebookfrenzy.withmap.network.WithMapService
import com.ebookfrenzy.withmap.network.response.CommonPinInfo
import com.ebookfrenzy.withmap.network.response.PinDetail
import com.ebookfrenzy.withmap.network.response.PinDetailExtra
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
    val isRecommended: LiveData<Boolean>
        get() = _isRecommended

    private val _isReported = MutableLiveData<Boolean>()
    val isReported: LiveData<Boolean>
        get() = _isReported

    private val _isMyPin = MutableLiveData<Boolean>()
    val isMyPin: LiveData<Boolean>
        get() = _isMyPin

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun getPinDetail(pinId: Int) {
        _isRecommended.value = false
        _isReported.value = false
        _isMyPin.value = false
        val token =
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtb2RlbG1ha2VyQG5hdmVyLmNvbSIsIm5pY2tuYW1lIjoic3RyaW5nIiwiaXNzIjoic3ByaW5nLmp3dC5pc3N1ZXIiLCJpYXQiOjE1Njk2NjE0OTcsImV4cCI6MTU3MDI2NjI5Nn0.5G0oqmlR-0aPzzb7rC9GQcTmc0wR4awQuj1d2bKcHmA"
        addDisposable(
            withMapService.getPinDetails(token, pinId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _pinDetail.value = pin1stub
                        _isRecommended.value = it.recommended
                        _isMyPin.value = it.mine
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

val pin1stub = PinDetail(
    PinDetailExtra(
        1,
        "길이 불편해요", "",
        "", null,
        -1, ""
    ),
    CommonPinInfo(
        "", "",
        1, null, "unimproved",
        "improved", 1,
        0.0, 0.0, "주소주소",
        false, 999
    ),
    listOf(),
    mine = false,
    recommended = false
)

val pin5stub = PinDetail(
    PinDetailExtra(
        1,
        "", "정시(09:00 ~ 22:30)",
        "02-399-1553", null,
        -1, ""
    ),
    CommonPinInfo(
        "", "",
        1, null, "unimproved",
        "improved", 5,
        0.0, 0.0, "주소주소",
        false, 999
    ),
    listOf(),
    mine = false,
    recommended = false
)

val pin6stub = PinDetail(
    PinDetailExtra(
        1,
        "여기 맛집이네요", "24시",
        "010-1234-1234", "010-1234-1234",
        0, "www.~~~.com"
    ),
    CommonPinInfo(
        "", "",
        1, null, "unimproved",
        "improved", 6,
        0.0, 0.0, "주소주소",
        false, 999
    ),
    listOf(),
    mine = false,
    recommended = false
)