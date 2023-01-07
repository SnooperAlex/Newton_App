package com.example.newton

import com.example.newton.api.JSON_NewsAPI
import retrofit2.http.GET

interface RequestAPI {

    @GET("/v2/top-headlines?country=us&apiKey=f6bb2b46ff794c89a5ad68fdcb4f5c2e")
    suspend fun getNews(): JSON_NewsAPI

}