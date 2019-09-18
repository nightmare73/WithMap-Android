package com.ebookfrenzy.withmap.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ebookfrenzy.withmap.data.MarkerItem
import com.ebookfrenzy.withmap.data.getMarkerItems
import com.google.android.gms.maps.model.Marker

class MainViewModel : ViewModel() {
    private val TAG = "MainViewModel"

    val markerItemLiveData = MutableLiveData<List<MarkerItem>>()

    val selectedMarkerLiveData = MutableLiveData<Marker>()

    val notification

    init {
        markerItemLiveData.postValue(getMarkerItems())
        printSelectedMarkerLiveData()
    }
    fun printSelectedMarkerLiveData() {
        if(selectedMarkerLiveData.value != null) {
            Log.d(TAG, "selectedMarkerLiveData : ${selectedMarkerLiveData.value!!.tag}")
        }
    }
}