package com.example.libraryapproom.api.network

import com.example.libraryapproom.api.ApiService

object Common {
    private val BASE_URL = "https://springbootlibrary2022.herokuapp.com/"
    val retrofitService: ApiService
        get() = RetrofitClient.getClient(BASE_URL).create(ApiService::class.java)
}