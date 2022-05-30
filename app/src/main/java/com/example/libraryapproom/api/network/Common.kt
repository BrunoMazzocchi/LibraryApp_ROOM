package com.example.libraryapproom.api.network

import com.example.libraryapproom.api.ApiService

object Common {
    //heroku
    private val BASE_URL = "https://springbootlibrary2022.herokuapp.com/"
    //API local
    //private val BASE_URL = "http://192.168.0.15:9091/"
    val retrofitService: ApiService
        get() = RetrofitClient.getClient(BASE_URL).create(ApiService::class.java)
}