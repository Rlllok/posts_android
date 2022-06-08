package com.example.posts.models

data class Comment(
    val id: Int,
    val created_date: String,
    val text: String,
    val owner: String,
    val post: Int,
)