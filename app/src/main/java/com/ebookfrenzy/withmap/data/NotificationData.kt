package com.ebookfrenzy.withmap.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotificationData(
    val type: Int,
    val title: String,
    val date: String
): Parcelable

fun getNotification() = mutableListOf(
    NotificationData(1, "7월 개선위치 안내", "19.07.17"),
    NotificationData(2, "8월 한 달간 기여도 적립 두배!", "19.08.18"),
    NotificationData(3, "울퉁불퉁 비포장 길이 추천을 받았어요", "19.09.19")
)

/**
fun getNotification() : MutableList<NotificationData>{
return mutableListOf(NotificationData(1, "first", "19.09.17"),
NotificationData(2, "second", "19.09.18"),
NotificationData(3, "third", "19.09.19"))
 */