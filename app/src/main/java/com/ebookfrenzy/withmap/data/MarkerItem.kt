package com.ebookfrenzy.withmap.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarkerItem(
    val latitude: Double,
    val longitude: Double,
    val type : Int,
    val name : String,
    val crtDate : String,
    val address : String,
    var improved: Boolean,
    var improvedTitle : String? = null,
    var improvedDate : String? = null,
    var comment : String? = null,
    var useable_time : String? = null,
    var call_number : String? = null
) : Parcelable



fun getMarkerItems() = mutableListOf<MarkerItem>(

    MarkerItem(37.538523, 126.96568, 1, "구부정한 길", "20.19.19", "서울시 마포구", false),
    MarkerItem(37.527523, 126.96568, 2, "미끌미끌 도로", "20.20.20","서울시 강남구", false),
    MarkerItem(37.549523, 126.96568, 3, "경사로", "20.18.19","서울시 영등포구",true, "매끈 경사로", "22.19.19"),
    MarkerItem(37.538523, 126.95768, 5, "내리막", "20.17.23","서울시 사당동",false, "꺼끌 내리막", "22.20.20", null, "3시~ 7시", "01010101010")

)