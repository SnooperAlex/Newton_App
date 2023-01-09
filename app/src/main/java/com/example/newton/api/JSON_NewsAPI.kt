package com.example.newton.api

data class JSON_NewsAPI(
    val news: List<New>,
    val page: Int,
    val status: String
)