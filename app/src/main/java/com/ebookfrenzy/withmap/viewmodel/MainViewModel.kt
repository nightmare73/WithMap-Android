package com.ebookfrenzy.withmap.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.ebookfrenzy.withmap.data.MarkerItem
import com.ebookfrenzy.withmap.data.getMarkerItems
import com.google.android.gms.maps.model.Marker

class MainViewModel : ViewModel() {
    private val TAG = "MainViewModel"

    val markerItemLiveData = MutableLiveData<List<MarkerItem>>()

    val selectedMarkerLiveData = MutableLiveData<Marker?>()

    // 0: bottomsheet 내려져있는 상태, 1: 전에꺼 unImproved , 2: 전에꺼 improved
    val beforeSelectedwasImproved = MutableLiveData<Int>().apply {
        value = 0
    }
    val bottomSheetUpdate = MutableLiveData<Boolean>()

    init {
        markerItemLiveData.postValue(getMarkerItems())
        printSelectedMarkerLiveData()

        selectedMarkerLiveData.observeForever(Observer{

            Log.d(TAG, "beforeSelectedwasImproved.value : ${beforeSelectedwasImproved.value}")
            if(selectedMarkerLiveData.value != null) {
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
    }

    //새로 선택된 마커의 개선유무를 Int로 만들기 -> 전의 상태랑 비교
    fun selectedMarkerStatusToInt(i : Boolean) : Int{
        when(i) {
            true -> return 2 //개선 된 상태
            false -> return 1 // 개선 안된 상태
        }
    }

    fun printSelectedMarkerLiveData() {
        if(selectedMarkerLiveData.value != null) {
            Log.d(TAG, "selectedMarkerLiveData : ${selectedMarkerLiveData.value!!.tag}")
        }
    }
}