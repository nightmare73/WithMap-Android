package com.ebookfrenzy.withmap.data

import java.io.Serializable

/**
 * Created By Yun Hyeok
 * on 9월 14, 2019
 */

data class SearchLocationResult(
    val id: String,

    val name: String,
    val address: String,
    val roadAddress: String?,

    val latitude: Double,
    val longitude: Double
):Serializable