package com.ebookfrenzy.withmap.network

import com.ebookfrenzy.withmap.data.GetMyRegisterPinData
import com.ebookfrenzy.withmap.network.request.SignInParams
import com.ebookfrenzy.withmap.network.request.SignUpParams
import com.ebookfrenzy.withmap.network.response.*
import com.ebookfrenzy.withmap.network.response.pinregister.PinRegisterData
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    // 회원 가입
    @POST("/withmap/users")
    fun requestSignUp(
        @Body body: SignUpParams
    ): Completable

    // 중복 확인
    @GET("/withmap/users/{content}/check")
    fun checkDuplicated(
        @Path("content") type: String,
        @Query("query") query: String
    ): Single<CheckDuplicatedResponse>

    // 나의 정보조회
    @GET("/withmap/users/myinfo")
    fun getUserInfo(
        @Header("Authorization") token: String
    ) //UserInfo class 만들어놓음

    // 핀 작성
    @Multipart
    @POST("/withmap/pins")
    fun postPinRegister(
        @Header("Authorization") token : String,
        @PartMap() pin : Map<String, @JvmSuppressWildcards RequestBody>,
        @Part files : ArrayList<MultipartBody.Part>?
    ):Call<PostPinRegisterResponse>

    //내가 등록한 핀
    @GET("/withmap/users/mypins")
    fun getMyRegisterPin(
        @Header("Authorization") token : String
    ):Call<List<GetMyRegisterPinData>>

    // 주위 핀 조회
    @GET("/withmap/pins")
    fun getPinsAroundPosition(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Single<List<CommonPinInfo>>

    @GET("/withmap/users/myinfo")
    fun getUserInfo2(
        @Header("Authorization") token : String
    ) : Call<UserInfo>

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