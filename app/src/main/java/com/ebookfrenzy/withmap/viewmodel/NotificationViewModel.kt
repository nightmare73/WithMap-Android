package com.ebookfrenzy.withmap.viewmodel

import android.widget.Button
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.data.MyRegisterPinData
import com.ebookfrenzy.withmap.data.NotificationData
import com.ebookfrenzy.withmap.data.getMyRegisterPin
import com.ebookfrenzy.withmap.data.getNotification

class NotificationViewModel : ViewModel() {

    val notificationLiveData = MutableLiveData<MutableList<NotificationData>>()

    val myRegisterPinLiveData = MutableLiveData<MutableList<MyRegisterPinData>>()

    init{
        notificationLiveData.postValue(getNotification())
        myRegisterPinLiveData.postValue(getMyRegisterPin())
    }

    @BindingAdapter("setImage")
    fun Button.setImage(size : Int) {
        if(size>0) {
            this.setBackgroundResource(R.drawable.home_burger_notice)

        }
    }


}