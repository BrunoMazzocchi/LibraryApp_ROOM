package com.example.libraryapproom.api

import com.example.libraryapproom.api.dataClass.Author
import com.example.libraryapproom.api.dataClass.Books
import com.example.libraryapproom.api.dataClass.Borrow
import com.example.libraryapproom.api.dataClass.Type
import com.example.libraryapproom.bd.entidades.LibrosModels
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @GET
    suspend fun getAuthor (@Url url: String): Response<Author>

    @GET("books/all")
    suspend fun getAllBooks(): ArrayList<Books>

    @GET("author/all")
    suspend fun getAllAuthors(): ArrayList<Author>

    @GET("genre/all")
    suspend fun getAllGenre(): ArrayList<Type>

    @DELETE("books/delete/{id}")
    suspend fun deleteBook(@Path("id") id: Int?): Response<Void>


    @GET
    suspend fun getBook(@Url url:String):Response<Books>

    @GET
    suspend fun getType (@Url url: String): Response<Type>
    @GET
    suspend fun getBorrow(@Url url: String): Response<Borrow>

    @POST
    suspend fun postBook()


    @POST("books/save")
    suspend fun editABook(@Body requestBody: RequestBody):Response<ResponseBody>
}