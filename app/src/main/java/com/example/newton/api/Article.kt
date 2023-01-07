package com.example.newton.api

data class Article(
    val author: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String
)