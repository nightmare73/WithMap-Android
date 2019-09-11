package com.ebookfrenzy.withmap.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PinRegisterViewModel : ViewModel() {

   val albumImageListLiveData = MutableLiveData<MutableList<Uri>>()



}