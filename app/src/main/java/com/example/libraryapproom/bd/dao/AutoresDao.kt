package com.example.libraryapproom.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.libraryapproom.bd.entidades.AuthorsEntity


@Dao
interface AutoresDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg autores: AuthorsEntity)

    @Query("SELECT * FROM TblAutores")
    suspend fun getAllAuthors(): List<AuthorsEntity>

    @Query("SELECT * FROM TblAutores")
    fun getAllRealData(): LiveData<List<AuthorsEntity>>
}