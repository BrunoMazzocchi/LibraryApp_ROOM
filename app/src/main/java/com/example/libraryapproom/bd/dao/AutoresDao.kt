package com.example.libraryapproom.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.libraryapproom.bd.entidades.AutoresEntity
import com.example.libraryapproom.bd.entidades.LibrosModels

@Dao
interface AutoresDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg autores: AutoresEntity)

    @Query("SELECT * FROM TblAutores")
    suspend fun getAll(): List<AutoresEntity>

    @Query("SELECT * FROM TblAutores")
    fun getAllRealData(): LiveData<List<AutoresEntity>>
}