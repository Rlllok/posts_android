package com.example.posts.api

import com.example.posts.models.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface PostsApi {
    @GET("/posts/")
    suspend fun getPosts() : Response<List<Post>>

    @GET("/posts/{id}/comments/")
    suspend fun getCommentsToPost(
        @Path("id") id: Int
    ) : Response<List<Comment>>

    @POST("/api/token/")
    fun  getToken(
        @Body info: LogInBody
    ): Call<Token>

    @POST("/api/register/")
    fun createAccount(
        @Body info: RegistrationBody
    ): Call<RegistrationResponse>
}