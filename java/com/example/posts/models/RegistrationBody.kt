package com.example.posts.models

data class RegistrationBody(
    val email: String,
    val username: String,
    val password: String,
    val full_name: String,
    val birth_date: String,
    val bio: String,
)
