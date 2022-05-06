package com.example.libraryapproom.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.libraryapproom.bd.entidades.LibrosModels

@Dao
interface LibrosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg libros: LibrosModels)

    @Query("SELECT * FROM LibrosModels")
    suspend fun getAll(): List<LibrosModels>

    @Query("SELECT * FROM LibrosModels")
    fun getAllRealData(): LiveData<List<LibrosModels>>

    @Update
    suspend fun update(libros: LibrosModels)

    @Delete
    suspend fun delete(libros: LibrosModels)

    @Query("Delete from LibrosModels")
    suspend fun deleteAll()
}