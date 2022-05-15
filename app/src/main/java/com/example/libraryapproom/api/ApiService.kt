package com.example.libraryapproom.api

import com.example.libraryapproom.api.dataClass.Author
import com.example.libraryapproom.api.dataClass.Books
import com.example.libraryapproom.api.dataClass.Borrow
import com.example.libraryapproom.api.dataClass.Type
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @GET
    suspend fun getAuthor (@Url url: String): Response<Author>

    @GET("all")
    suspend fun getAllBooks(): ArrayList<Books>

    @DELETE("delete/{id}")
    suspend fun deleteBook(@Path("id") id: Int?): Response<Void>


    @GET
    suspend fun getBook(@Url url:String):Response<Books>

    @GET
    suspend fun getType (@Url url: String): Response<Type>
    @GET
    suspend fun getBorrow(@Url url: String): Response<Borrow>

    @POST
    suspend fun postBook()
}