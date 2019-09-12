package com.ebookfrenzy.withmap.data

data class MyRegisterPinData (
    val type : Int,
    val title : String,
    val date : String
)

fun getMyRegisterPin() = mutableListOf<MyRegisterPinData>(
    MyRegisterPinData(1, "첫번째", "19.09.17"),
    MyRegisterPinData(2, "두번째", "19.09.18"),
    MyRegisterPinData(3, "세번째", "19.09.19")
    )