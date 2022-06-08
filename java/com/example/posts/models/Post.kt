package com.example.posts.models

data class Post(
    val id: Int,
    val created_date: String,
    val owner: String,
    val title: String,
    val content: String,
    val comments: List<Int>,
)