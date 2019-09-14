package com.ebookfrenzy.withmap.data

data class MarkerItem(
    val lat: Double,
    val lon: Double
)



fun getMarkerItems() = mutableListOf<MarkerItem>(

    MarkerItem(37.538523, 126.96568),
    MarkerItem(37.527523, 126.96568),
    MarkerItem(37.549523, 126.96568),
    MarkerItem(37.538523, 126.95768)

)