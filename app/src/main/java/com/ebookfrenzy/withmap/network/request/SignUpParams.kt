package com.ebookfrenzy.withmap.network.request

/**
 * Created By Yun Hyeok
 * on 9월 25, 2019
 */

data class SignUpParams(
    val disable: String,
    val email: String,
    val gender: String,
    val name: String,
    val password: String,
    val point: Int,
    val year: Int
)