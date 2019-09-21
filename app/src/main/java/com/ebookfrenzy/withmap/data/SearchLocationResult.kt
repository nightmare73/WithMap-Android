package com.ebookfrenzy.withmap.data

/**
 * Created By Yun Hyeok
 * on 9월 14, 2019
 */

data class SearchLocationResult(
    val id: String,

    val name: String,
    val address: String,

    val latitude: Double,
    val longitude: Double
)