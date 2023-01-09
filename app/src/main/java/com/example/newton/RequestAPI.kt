package com.example.newton

import com.example.newton.api.JSON_NewsAPI
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestAPI {

    @GET("/v1/latest-news?&language=en&apiKey=qJeaftCDwwSlcrGxO32Oj05AsVG7_MGH1vKipJ7jxUKwBdoT")
    suspend fun getLatestNews(): JSON_NewsAPI

    @GET("/v1/latest-news?&language=en&apiKey=qJeaftCDwwSlcrGxO32Oj05AsVG7_MGH1vKipJ7jxUKwBdoT")
    suspend fun getFilteredNews(
        @Query("category") category: String
    ): JSON_NewsAPI

    @GET("/v1/search")
    suspend fun searchNews(
        @Query("keywords") keywords: String,
        @Query("apiKey") apiKey: String
    ): JSON_NewsAPI



}