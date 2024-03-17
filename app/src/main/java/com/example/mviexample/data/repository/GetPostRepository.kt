package com.example.mviexample.data.repository

import com.example.mviexample.data.api.ApiService

class GetPostRepository(private val apiService: ApiService) {

    suspend fun getposts() = apiService.getPosts()
}