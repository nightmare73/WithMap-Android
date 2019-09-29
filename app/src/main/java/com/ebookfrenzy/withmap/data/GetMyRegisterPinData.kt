package com.ebookfrenzy.withmap.data

data class GetMyRegisterPinData(
    val address : String,
    val comment : String,
    val crtDate : String,
    val id : Int,
    val improvedName : String,
    val latitude : Double,
    val likeCount : Int,
    val longitude : Int,
    val state : Boolean,
    val type : Int,
    val unimprovedName : String,
    val updDate : String,
    val userId : Int
)
