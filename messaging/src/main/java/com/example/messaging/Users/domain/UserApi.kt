package com.example.messaging.Users.domain

import com.example.messaging.Users.data.User
import retrofit2.http.GET

interface UserApi {
    @GET("users") // Have to replace with actual endpoint
    suspend fun getUsers(): List<User>
}