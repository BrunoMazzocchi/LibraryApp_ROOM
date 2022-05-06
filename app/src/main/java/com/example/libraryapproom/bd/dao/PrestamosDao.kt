package com.example.libraryapproom.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.libraryapproom.bd.entidades.PrestamosEntity

@Dao
interface PrestamosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prestamo: PrestamosEntity)

    @Query("SELECT * FROM TblPrestamos")
    suspend fun getAll(): List<PrestamosEntity>

    @Query("SELECT * FROM TblPrestamos")
    fun getAllRealData(): LiveData<List<PrestamosEntity>>

    @Update
    fun update(prestamo: PrestamosEntity)

    @Delete
    fun delete(prestamo: PrestamosEntity)

    @Query("DELETE FROM TblPrestamos")
    suspend fun deleteAll()
}