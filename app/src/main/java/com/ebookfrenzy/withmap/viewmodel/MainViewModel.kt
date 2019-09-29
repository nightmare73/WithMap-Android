package com.ebookfrenzy.withmap.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.ebookfrenzy.withmap.config.WithMapApplication
import com.ebookfrenzy.withmap.data.MarkerItem
import com.ebookfrenzy.withmap.data.getMarkerItems
import com.ebookfrenzy.withmap.network.response.CommonPinInfo
import com.ebookfrenzy.withmap.network.response.DataModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(val model: DataModel) : BaseViewModel() {

    private val TAG = "MainViewModel"

    val centerLatLng: MutableLiveData<LatLng> = MutableLiveData<LatLng>()

    private val _aroundPinResponseLiveData = MutableLiveData<List<CommonPinInfo>>()
    val aroundPinResponseLiveData: LiveData<List<CommonPinInfo>>
        get() = _aroundPinResponseLiveData
    val markerItemLiveData = MutableLiveData<List<MarkerItem>>()
    val selectedMarkerLiveData = MutableLiveData<Marker?>()
    // 0: bottomsheet 내려져있는 상태, 1: 전에꺼 unImproved , 2: 전에꺼 improved
    val beforeSelectedwasImproved = MutableLiveData<Int>().apply {
        value = 0
    }
    val bottomSheetUpdate = MutableLiveData<Boolean>()

    //주변핀 조회
    fun getPinsAround(latitude: Double, longitude: Double) {
        Log.d(TAG, "getPinsAround()")
        addDisposable(
            model.getData(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.run {
                        Log.d(TAG, "run")
                        if (this.size > 0) {
                            Log.d(TAG, "List<CommonPinInfo> : $this")
                            markerItemLiveData.value = this.map {
                                MarkerItem(
                                    it.latitude,
                                    it.longitude,
                                    it.type,
                                    when (it.type) {
                                        5, 6 -> it.improvedName
                                        else -> it.unimprovedName
                                    },
                                    it.crtDate,
                                    it.address,
                                    it.state,
                                    it.improvedName,
                                    it.updDate,
                                    it.comment
                                )

                        }
                    }
                }
    },
    {
        Log.d(TAG, "response error, message : ${TextUtils.join("\n", it.stackTrace)}")
    })
    )
}
//
//        Log.d(TAG, "getAroundPins()")
//        val networkService = WithMapApplication.instance.networkService
//        val getPinsAroundPosition2 = networkService.getPinsAroundPosition2(
//            "application/json",latitude, longitude
//        )
//        getPinsAroundPosition2.enqueue(object : Callback<ArrayList<CommonPinInfo>> {
//            override fun onFailure(call: Call<ArrayList<CommonPinInfo>>, t: Throwable) {
//                Log.e(TAG, "error : ${TextUtils.join("\n", t.stackTrace)}")
//            }
//
//            override fun onResponse(
//                call: Call<ArrayList<CommonPinInfo>>,
//                response: Response<ArrayList<CommonPinInfo>>
//            ) {
//                if (response.isSuccessful) {
//                    Log.d(TAG, "getPindAroundPositioon2 success")
//                    markerItemLiveData.value = null
//                    if (response.body() != null) {
//                        val it = response.body() as List<CommonPinInfo>
//                        Log.d(TAG, "run")
//                        it.run {
//                            if (this.size > 0) {
//                                Log.d(TAG, "List<CommonPinInfo> : $this")
//                                markerItemLiveData.value = this.map {
//                                    MarkerItem(
//                                        it.latitude,
//                                        it.longitude,
//                                        it.type,
//                                        it.improvedName,
//                                        it.crtDate,
//                                        it.address,
//                                        it.state,
//                                        it.improvedName,
//                                        it.updDate
//                                    )
//                                }
//                            }
//                        }
//                    }else {
//                        markerItemLiveData.value = null
//                        Log.d(TAG, "pinsAround is null")
//                    }
//                }
//            }
//        })
//    }


init {
//        getPinsAround(37.537523, 126.96558)
    printSelectedMarkerLiveData()

    selectedMarkerLiveData.observeForever(Observer {
        if (selectedMarkerLiveData.value != null) {
            Log.d(
                TAG,
                "selectedMarkerLiveData : ${(it!!.tag as MarkerItem).toString()}"
            )
        }
        Log.d(
            TAG,
            "beforeSelectedwasImproved.value : ${beforeSelectedwasImproved.value}"
        )
        if (selectedMarkerLiveData.value != null) {
            if (selectedMarkerStatusToInt((it!!.tag as MarkerItem).improved) == beforeSelectedwasImproved.value) {
                bottomSheetUpdate.postValue(false)
                beforeSelectedwasImproved.value =
                    selectedMarkerStatusToInt((it.tag as MarkerItem).improved)
            } else {
                bottomSheetUpdate.postValue(true)
                beforeSelectedwasImproved.value =
                    selectedMarkerStatusToInt((it.tag as MarkerItem).improved)
            }
        }

        Log.d(TAG, "bottomSheetUpdate : ${bottomSheetUpdate.value}")
    })

    markerItemLiveData.observeForever(Observer {
        Log.d(TAG, "markeritemLiveData : ${it}")
    })
}

//새로 선택된 마커의 개선유무를 Int로 만들기 -> 전의 상태랑 비교
fun selectedMarkerStatusToInt(i: Boolean): Int {
    when (i) {
        true -> return 2 //개선 된 상태
        false -> return 1 // 개선 안된 상태
    }
}

fun printSelectedMarkerLiveData() {
    if (selectedMarkerLiveData.value != null) {
        Log.d(TAG, "selectedMarkerLiveData : ${selectedMarkerLiveData.value!!.tag}")
    }
}
}