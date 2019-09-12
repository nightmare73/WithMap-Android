package com.ebookfrenzy.withmap.data

data class NotificationData (

    val type : Int,
    val title : String,
    val date : String
)

fun getNotification() : MutableList<NotificationData>{
    return mutableListOf(NotificationData(1, "first", "19.09.17"),
        NotificationData(2, "second", "19.09.18"),
        NotificationData(3, "third", "19.09.19"))
}