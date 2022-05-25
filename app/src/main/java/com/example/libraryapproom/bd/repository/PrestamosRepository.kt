package com.example.libraryapproom.bd.repository

import androidx.lifecycle.LiveData
import com.example.libraryapproom.bd.dao.EstudiantesDao
import com.example.libraryapproom.bd.dao.LibrosDao
import com.example.libraryapproom.bd.dao.PrestamosDao
import com.example.libraryapproom.bd.entidades.EstudiantesEntity
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.bd.entidades.PrestamosEntity
import com.example.libraryapproom.bd.entidades.vistas.view_borrows

class PrestamosRepository(private val dao: PrestamosDao,
    val daoBooks: LibrosDao,
    val daoStudents: EstudiantesDao
    ) {
    val listado : LiveData<List<view_borrows>> =
      dao.getAllRealData()

    suspend fun books(): List<LibrosModels>{
        return daoBooks.getAll()
    }

    suspend fun students(): List<EstudiantesEntity>{
        return daoStudents.getAllStudents()
    }

    suspend fun addPrestamo(prestamo: PrestamosEntity){
        dao.insert(prestamo)
    }

    suspend fun updatePrestamo(prestamo: PrestamosEntity){
        dao.update(prestamo)
    }

    suspend fun deletePrestamo(prestamo: Int){
        dao.delete(prestamo)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }
}