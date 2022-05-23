package com.example.libraryapproom.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.bd.entidades.vistas.view_books

@Dao
interface LibrosDao {

    //Vw_books
    @Query("SELECT * FROM view_books b")
    fun getAllRealData(): LiveData<List<view_books>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg libros: LibrosModels)

    @Query("SELECT * FROM LibrosModels")
    suspend fun getAll(): List<LibrosModels>

    @Update
    suspend fun update(libros: LibrosModels)

    @Delete
    suspend fun delete(libros: LibrosModels)

    @Query("Delete from LibrosModels")
    suspend fun deleteAll()


    //SPINERS
    @Query("SELECT autorId FROM TblAutores where autorId= :nameA")
    suspend fun getByStringAutores(nameA: Int): Int

    @Query("SELECT type_id FROM TblType where type_id= :nameType")
    suspend fun getByStringType(nameType: Int): Int

}