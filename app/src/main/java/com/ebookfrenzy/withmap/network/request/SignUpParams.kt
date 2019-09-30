package com.ebookfrenzy.withmap.network.request

import java.io.Serializable

/**
 * Created By Yun Hyeok
 * on 9월 25, 2019
 */

data class SignUpParams(
    val email: String,
    val name: String,
    val password: String,

    var disable: String? = null,
    var gender: String? = null,
    var point: Int? = null,
    var year: Int? = null
) : Serializable