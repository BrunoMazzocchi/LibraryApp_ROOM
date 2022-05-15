package com.example.libraryapproom.bd.repository

import androidx.lifecycle.LiveData
import com.example.libraryapproom.bd.dao.LibrosDao
import com.example.libraryapproom.bd.entidades.LibrosModels

class LibrosRepository(private val dao: LibrosDao) {
    val listado: LiveData<List<LibrosModels>> =
        dao.getAllRealData()


    suspend fun addLibro(libros: LibrosModels) {
        dao.insert(libros)
    }

    suspend fun updateLibro(libros: LibrosModels) {
        dao.update(libros)
    }

    suspend fun deleteLibro(libros: LibrosModels) {
        dao.delete(libros)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}