package com.ebookfrenzy.withmap.viewmodel

import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.ebookfrenzy.withmap.R
import com.ebookfrenzy.withmap.config.WithMapApplication
import com.ebookfrenzy.withmap.data.*
import com.ebookfrenzy.withmap.network.response.DataModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationViewModel : BaseViewModel() {
    private val TAG = "NotificationViewModel"
    var notificationSize = MutableLiveData<String>()

    val notificationLiveData = MutableLiveData<MutableList<NotificationData>>()


    val myRegisterPinLiveData = MutableLiveData<List<MyRegisterPinData>>()

    init {
        notificationLiveData.postValue(getNotification())
        getMyRegisterPin()

        notificationLiveData.observeForever(Observer {
            if (notificationLiveData.value != null)
                if (notificationLiveData.value!!.size > 0) {
                    notificationSize.value = notificationLiveData.value!!.size.toString()
                    Log.d(TAG, notificationSize.value)
                }
        })
    }

    fun getMyRegisterPin() {
        Log.d(TAG, "getMyRegisterPin()")
//        addDisposable(
//            model.getMyRegisterPin("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtb2RlbG1ha2VyQG5hdmVyLmNvbSIsIm5pY2tuYW1lIjoic3RyaW5nIiwiaXNzIjoic3ByaW5nLmp3dC5pc3N1ZXIiLCJpYXQiOjE1Njk2NjE0OTcsImV4cCI6MTU3MDI2NjI5Nn0.5G0oqmlR-0aPzzb7rC9GQcTmc0wR4awQuj1d2bKcHmA")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    it.run{
//                        Log.d(TAG, "run")
//                        if(this.size>0) {
//                            Log.d(TAG, "List<GetMyRegisterPin> : $this")
//                            myRegisterPinLiveData.value = this.map{
//                                MyRegisterPinData(
//                                    it.type,
//                                    if(it.state) it.improvedName else it.unimprovedName,
//                                    it.crtDate
//                                )
//                            }
//                        }
//                    }
//                },{
//                    Log.e(TAG, "getMyRegisterPin error ${TextUtils.join("\n", it.stackTrace)}")
//                })
//        )
        val networkService = WithMapApplication.instance.networkService
        val getMyRegisterPin = networkService.getMyRegisterPin("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtb2RlbG1ha2VyQG5hdmVyLmNvbSIsIm5pY2tuYW1lIjoic3RyaW5nIiwiaXNzIjoic3ByaW5nLmp3dC5pc3N1ZXIiLCJpYXQiOjE1Njk2NjE0OTcsImV4cCI6MTU3MDI2NjI5Nn0.5G0oqmlR-0aPzzb7rC9GQcTmc0wR4awQuj1d2bKcHmA")
        getMyRegisterPin.enqueue(object : Callback<List<GetMyRegisterPinData>>{
            override fun onFailure(call: Call<List<GetMyRegisterPinData>>, t: Throwable) {
                Log.e(TAG, t.stackTrace.toString())
            }

            override fun onResponse(
                call: Call<List<GetMyRegisterPinData>>,
                response: Response<List<GetMyRegisterPinData>>
            ) {
                if(response.isSuccessful) {
                    Log.d(TAG, "getMyRegisterPin success")
                    if(response.body() != null) {
                        val it = response.body() as List<GetMyRegisterPinData>
                        Log.d(TAG, it.size.toString())
                        it.run{
                            if(this.size>0) {
                                myRegisterPinLiveData.value = this.map{
                                    MyRegisterPinData(
                                        it.type,
                                        if(it.state) it.improvedName else it.unimprovedName,
                                        it.crtDate
                                        )
                                }
                            }
                        }
                    }else {
                        Log.d(TAG, "myRegisterPinData is null")
                    }
                }else{
                    Log.d(TAG, response.message())
                }
            }
        })
    }


    fun goToMyRegisterDetail() {
        Log.d(TAG, "MyRegister clicked")
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

fun hamSetImage(view : View, data: MutableList<NotificationData>) {
    if(data != null) {
        if(data!!.size>0) {
            (view as ImageView).setImageResource(R.drawable.ham_alarm_isnoti)
        }
    }
}
