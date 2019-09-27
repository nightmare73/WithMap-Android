package com.ebookfrenzy.withmap.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.ebookfrenzy.withmap.data.MarkerItem
import com.ebookfrenzy.withmap.data.getMarkerItems
import com.ebookfrenzy.withmap.network.response.CommonPinInfo
import com.ebookfrenzy.withmap.network.response.DataModel
import com.google.android.gms.maps.model.Marker
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(val model: DataModel ) : BaseViewModel() {

    private val TAG = "MainViewModel"

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

    fun getPinsAround(latitude: Double, longitude: Double) {
        Log.d(TAG, "getPinsAround()")
        addDisposable(
            model.getData(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.isEmpty()) {
                        Log.d(TAG, "비어있음")
                        return@subscribe
                    }
                    it.run {
                        Log.d(TAG, "run")
                        if (this.size > 0) {
                            Log.d(TAG, "List<CommonPinInfo> : $this")
                            markerItemLiveData.value = this.map {
                                MarkerItem(
                                    it.latitude,
                                    it.longitude,
                                    it.type,
                                    it.unimprovedName!!,
                                    it.crtDate,
                                    it.address,
                                    it.state,
                                    it.improvedName,
                                    it.updDate
                                )
                            }
                        }
                    }
                }, {
                    Log.d(TAG, "response error, message : ${TextUtils.join("\n", it.stackTrace)}")
                })
        )
    }


    init {
//        getPinsAround(37.537523, 126.96558)
        printSelectedMarkerLiveData()

        selectedMarkerLiveData.observeForever(Observer {

            Log.d(TAG, "beforeSelectedwasImproved.value : ${beforeSelectedwasImproved.value}")
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