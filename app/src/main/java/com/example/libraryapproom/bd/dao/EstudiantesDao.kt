package com.example.libraryapproom.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.libraryapproom.bd.entidades.AuthorsEntity
import com.example.libraryapproom.bd.entidades.EstudiantesEntity

@Dao
interface EstudiantesDao  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg estudiante: EstudiantesEntity)

    @Query("SELECT * FROM TblEstudiantes")
    suspend fun getAllStudents(): List<EstudiantesEntity>

    @Query("SELECT * FROM TblEstudiantes")
    fun getAllRealData(): LiveData<List<EstudiantesEntity>>
}