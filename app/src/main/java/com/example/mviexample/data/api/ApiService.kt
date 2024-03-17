package com.example.mviexample.data.api

import com.example.mviexample.data.model.FakeDTO
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<FakeDTO>
}