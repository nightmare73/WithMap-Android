package com.ebookfrenzy.withmap.data

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.navigation.Navigation
import com.ebookfrenzy.withmap.R
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class MyRegisterPinData(
    val type : Int,
    val title : String,
    val date : String
) : Parcelable

fun getMyRegisterPin() = mutableListOf<MyRegisterPinData>(
    MyRegisterPinData(1, "첫번째", "19.09.17"),
    MyRegisterPinData(2, "두번째", "19.09.18"),
    MyRegisterPinData(3, "세번째", "19.09.19")
    )

