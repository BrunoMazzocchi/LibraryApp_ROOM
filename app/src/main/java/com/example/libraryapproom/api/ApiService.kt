package com.example.libraryapproom.api

import com.example.libraryapproom.api.dataClass.Author
import com.example.libraryapproom.api.dataClass.Books
import com.example.libraryapproom.api.dataClass.Borrow
import com.example.libraryapproom.api.dataClass.Type
import com.example.libraryapproom.bd.entidades.LibrosModels
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun getAuthor (@Url url: String): Response<Author>

    @GET("all")
    suspend fun  // as we are calling data from array so we are calling
    // it with array list and naming that method as getAllCourses();
    getAllBooks(): ArrayList<Books>


    @GET
    suspend fun getBook(@Url url:String):Response<Books>

    @GET
    suspend fun getType (@Url url: String): Response<Type>
    @GET
    suspend fun getBorrow(@Url url: String): Response<Borrow>
}