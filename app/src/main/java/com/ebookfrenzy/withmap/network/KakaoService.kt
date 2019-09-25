package com.ebookfrenzy.withmap.network

import com.ebookfrenzy.withmap.network.response.KakaoKeywordSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Created By Yun Hyeok
 * on 9월 12, 2019
 */

interface KakaoService {

    @GET("/v2/local/search/keyword.json")
    fun requestKakaoKeywordSearch(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 15
    ): Single<KakaoKeywordSearchResponse>

    companion object {
        const val baseUrl = "http://dapi.kakao.com"
    }
}