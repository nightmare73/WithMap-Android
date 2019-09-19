package com.ebookfrenzy.withmap.viewmodel

import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
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
}
@BindingAdapter("setImage")
fun setImage(view : View, data : MutableLiveData<MutableList<NotificationData>>) {
    if(data.value != null){
        if(data.value!!.size>0) {
            view.setBackgroundResource(R.drawable.home_burger_notice)
        }
    }
}