package com.ebookfrenzy.withmap.data

data class MarkerItem(
    val lat: Double,
    val lon: Double,
    val type : Int,
    val title : String,
    val date : String,
    val location : String,
    val improved: Boolean,
    val improvedTitle : String? = null,
    val improvedDate : String? = null
)



fun getMarkerItems() = mutableListOf<MarkerItem>(

    MarkerItem(37.538523, 126.96568, 1, "구부정한 길", "20.19.19", "서울시 마포구", false),
    MarkerItem(37.527523, 126.96568, 2, "미끌미끌 도로", "20.20.20","서울시 강남구", false),
    MarkerItem(37.549523, 126.96568, 3, "경사로", "20.18.19","서울시 영등포구",true, "매끈 경사로", "22.19.19"),
    MarkerItem(37.538523, 126.95768, 4, "내리막", "20.17.23","서울시 사당동",true, "꺼끌 내리막", "22.20.20")

)