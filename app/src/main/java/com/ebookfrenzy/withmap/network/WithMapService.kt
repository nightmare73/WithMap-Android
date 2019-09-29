package com.ebookfrenzy.withmap.network

import com.ebookfrenzy.withmap.network.request.SignInParams
import com.ebookfrenzy.withmap.network.request.SignUpParams
import com.ebookfrenzy.withmap.network.response.CommonPinInfo
import com.ebookfrenzy.withmap.network.response.PinDetail
import com.ebookfrenzy.withmap.network.response.SignInResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

/**
 * Created By Yun Hyeok
 * on 9월 25, 2019
 */

interface WithMapService {

    // 로그인
    @POST("/withmap/signin")
    fun requestSignIn(
        @Body body: SignInParams
    ): Single<SignInResponse>

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

    @POST

    // 주위 핀 조회
    @GET("/withmap/pins")
    fun getPinsAroundPosition(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Single<List<CommonPinInfo>>

    // 주위 핀 조회
    @GET("/withmap/pins")
    fun getPinsAroundPosition2(
        @Header("Content-Type") content_type: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Call<ArrayList<CommonPinInfo>>

    // 핀 자세히보기
    @GET("/withmap/pins/{id}")
    fun getPinDetails(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Single<PinDetail>

    // 핀 추천
    @PUT("/withmap/pins/{id}/like")
    fun recommendPin(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Completable

    // 핀 신고
    @PUT("/withmap/pins/{id}/report")
    fun reportPin(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Completable

    companion object {
        const val baseUrl = "http://withmap-253307.appspot.com"
    }
}