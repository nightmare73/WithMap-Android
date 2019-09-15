package com.ebookfrenzy.withmap.data

data class MarkerItem(
    val lat: Double,
    val lon: Double,
    val type : Int,
    val title : String
)



fun getMarkerItems() = mutableListOf<MarkerItem>(

    MarkerItem(37.538523, 126.96568, 1, "구부정한 길"),
    MarkerItem(37.527523, 126.96568, 2, "미끌미끌 도로"),
    MarkerItem(37.549523, 126.96568, 3, "경사로"),
    MarkerItem(37.538523, 126.95768, 4, "내리막")

)