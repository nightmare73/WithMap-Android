package com.ebookfrenzy.withmap.network.response

/**
 * Created By Yun Hyeok
 * on 9월 25, 2019
 */

data class UserInfo(
    val disable: String,
    val email: String,
    val gender: String,
    val name: String,
    val password: String,
    val point: Int,
    val year: Int
)