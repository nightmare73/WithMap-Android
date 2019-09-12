package com.ebookfrenzy.withmap.viewmodel

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.data.Notification
import kotlinx.android.synthetic.main.fragment_pin_register.view.*

class NotificationViewModel : ViewModel() {

    val notificationLiveData = MutableLiveData<MutableList<Notification>>()


}