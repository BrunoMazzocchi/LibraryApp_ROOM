package com.example.libraryapproom.bd.repository

import androidx.lifecycle.LiveData
import com.example.libraryapproom.bd.dao.PrestamosDao
import com.example.libraryapproom.bd.entidades.PrestamosEntity

class PrestamosRepository(private val dao: PrestamosDao) {
    val listado : LiveData<List<PrestamosEntity>> =
      dao.getAllRealData()

    suspend fun addPrestamo(prestamo: PrestamosEntity){
        dao.insert(prestamo)
    }

    suspend fun updatePrestamo(prestamo: PrestamosEntity){
        dao.update(prestamo)
    }

    suspend fun deleteGenero(prestamo: PrestamosEntity){
        dao.delete(prestamo)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }
}