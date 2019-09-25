package com.ebookfrenzy.withmap.network

import com.ebookfrenzy.withmap.network.request.SignInParams
import com.ebookfrenzy.withmap.network.request.SignUpParams
import com.ebookfrenzy.withmap.network.response.SignInResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created By Yun Hyeok
 * on 9월 25, 2019
 */

interface WithMapService {

    // 로그인
    @POST("/withmap/signin")
    fun requestSignIn(
        @Body body: SignInParams
    ): Call<SignInResponse>

    // 회원가입
    @POST("/withmap/users")
    fun requestSignUp(
        @Body body: SignUpParams
    ) // 반환 ???

    // 나의 정보조회
    @GET("/withmap/users/myinfo")
    fun getUserInfo(
        @Header("Authorization") token: String
    ) //UserInfo class 만들어놓음

    companion object {
        const val baseUrl = "http://withmap-253307.appspot.com"
    }
}