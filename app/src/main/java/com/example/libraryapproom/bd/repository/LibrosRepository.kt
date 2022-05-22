package com.example.libraryapproom.bd.repository

import androidx.lifecycle.LiveData
import com.example.libraryapproom.bd.dao.AutoresDao
import com.example.libraryapproom.bd.dao.LibrosDao
import com.example.libraryapproom.bd.dao.TypesDao
import com.example.libraryapproom.bd.entidades.AuthorsEntity
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.bd.entidades.TypesEntity
import com.example.libraryapproom.bd.entidades.vistas.view_books

class LibrosRepository(
    private val dao: LibrosDao,
val daoTp: TypesDao,
val daoAu: AutoresDao) {
    val listadoAll: LiveData<List<view_books>> = dao.getAllRealData()

    suspend fun types(): List<TypesEntity>{
        return daoTp.getAllTypes()
    }

    suspend fun authors(): List<AuthorsEntity>{
        return daoAu.getAllAuthors()
    }


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