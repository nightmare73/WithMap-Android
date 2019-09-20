package com.ebookfrenzy.withmap.viewmodel

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.data.MyRegisterPinData
import com.ebookfrenzy.withmap.data.NotificationData
import com.ebookfrenzy.withmap.data.getMyRegisterPin
import com.ebookfrenzy.withmap.data.getNotification

class NotificationViewModel : ViewModel() {
    private val TAG = "NotificationViewModel"
    var notificationSize = MutableLiveData<String>()

    val notificationLiveData = MutableLiveData<MutableList<NotificationData>>()


    val myRegisterPinLiveData = MutableLiveData<MutableList<MyRegisterPinData>>()

    init {
        notificationLiveData.postValue(getNotification())
        myRegisterPinLiveData.postValue(getMyRegisterPin())

        notificationLiveData.observeForever(Observer {
            if (notificationLiveData.value != null)
                if (notificationLiveData.value!!.size > 0) {
                    notificationSize.value = notificationLiveData.value!!.size.toString()
                    Log.d(TAG, notificationSize.value)
                }
        })
    }
}

@BindingAdapter("setImage")
fun setImage(view: View, data: MutableLiveData<MutableList<NotificationData>>) {
    if (data.value != null) {
        if (data.value!!.size > 0) {
            view.setBackgroundResource(R.drawable.home_burger_notice)
        }
    }
}