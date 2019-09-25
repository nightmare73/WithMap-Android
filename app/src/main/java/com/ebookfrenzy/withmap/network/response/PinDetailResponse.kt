package com.ebookfrenzy.withmap.network.response

/**
 * Created By Yun Hyeok
 * on 9월 25, 2019
 */

data class PinDetailResponse(
    val detailContents: String,
    val pin: CommonPinInfo,
    val pinImages: List<PinDetailImage>
)

data class CommonPinInfo(
    val address: String,
    val crtDate: String,   //how to use yyyy-mm-ddThh:mm:ss.733Z object in java
    val id: Int,
    val improvedName: String,
    val latitude: Double,
    val likeCount: Int,
    val longitude: Double,
    val state: Boolean,
    val type: Int,
    val unimprovedName: String,
    val updDate: String,
    val userId: Int
)

data class PinDetailImage(
    val imagePath: String,
    val state: Boolean
)

