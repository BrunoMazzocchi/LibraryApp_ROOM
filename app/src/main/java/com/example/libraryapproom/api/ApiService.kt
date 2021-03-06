package com.example.libraryapproom.api

import com.example.libraryapproom.api.dataClass.*
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

    @GET("borrows/all")
    suspend fun getAllBorrows(): ArrayList<Borrow>

    @POST("borrows/save")
    suspend fun addBorrow(@Body requestBody: RequestBody):Response<ResponseBody>

    @POST("books/save")
    suspend fun addABook(@Body requestBody: RequestBody):Response<ResponseBody>

    @GET("author/all")
    suspend fun getAllAuthors(): ArrayList<Author>

    @GET("type/all")
    suspend fun getAllType(): ArrayList<Type>

    @GET("student/all")
    suspend fun getAllStudents(): ArrayList<Student>

    @DELETE("books/delete/{id}")
    suspend fun deleteBook(@Path("id") id: Int?): Response<Void>

    @DELETE("borrows/delete/{id}")
    suspend fun deleteBorrow(@Path("id")id: Int?): Response<Void>

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

    @POST("borrows/save")
    suspend fun editBorrow(@Body requestBody: RequestBody):Response<ResponseBody>
}