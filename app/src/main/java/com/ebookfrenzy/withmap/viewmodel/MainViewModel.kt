package com.ebookfrenzy.withmap.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ebookfrenzy.withmap.data.MarkerItem
import com.ebookfrenzy.withmap.data.getMarkerItems

class MainViewModel : ViewModel() {

    val markerItemLiveData = MutableLiveData<MutableList<MarkerItem>>()

    init {
        markerItemLiveData.postValue(getMarkerItems())
    }
}