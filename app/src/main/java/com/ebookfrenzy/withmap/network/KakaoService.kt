package com.ebookfrenzy.withmap.network

import com.ebookfrenzy.withmap.network.response.KakaoKeywordAddressResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Created By Yun Hyeok
 * on 9월 12, 2019
 */

interface KakaoService {

    @GET("/v2/local/search/keyword.json")
    fun getKakaoKeywordAdressRequest(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 15
    ): Call<KakaoKeywordAddressResponse>

    companion object{
        const val baseUrl = "http://dapi.kakao.com"
    }
}