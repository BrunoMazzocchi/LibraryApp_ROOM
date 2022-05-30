package com.example.libraryapproom.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.libraryapproom.bd.entidades.PrestamosEntity
import com.example.libraryapproom.bd.entidades.vistas.view_books
import com.example.libraryapproom.bd.entidades.vistas.view_borrows
import kotlinx.coroutines.flow.Flow

@Dao
interface PrestamosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg prestamo: PrestamosEntity)

    @Query("SELECT * FROM TblPrestamos")
    suspend fun getAllPrestamos(): List<PrestamosEntity>

    @Query("SELECT * FROM view_borrows bo")
    fun getAllRealData(): LiveData<List<view_borrows>>

    @Update
    fun update(prestamo: PrestamosEntity)

    @Query("DELETE FROM TblPrestamos WHERE ID = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM TblPrestamos")
    suspend fun deleteAll()

    //Spiners
    @Query("SELECT student_id FROM TblEstudiantes WHERE student_id= :nameEst")
    suspend fun getByStringEstudiantes(nameEst: Int): Int

    @Query("SELECT ID FROM LibrosModels WHERE ID= :nameLib")
    suspend fun getByStringLibro(nameLib: Int): Int

    @Query("SELECT * FROM view_borrows WHERE student_name LIKE :query")
    fun searchDatabase(query: String): Flow<List<view_borrows>>
}