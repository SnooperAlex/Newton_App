package com.example.newton.api

data class JSON_NewsAPI(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)