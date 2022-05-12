package com.example.libraryapproom.bd.repository

import androidx.lifecycle.LiveData
import com.example.libraryapproom.bd.dao.AutoresDao
import com.example.libraryapproom.bd.dao.LibrosDao
import com.example.libraryapproom.bd.entidades.AutoresEntity
import com.example.libraryapproom.bd.entidades.LibrosModels

class AutoresRepository(private val dao: AutoresDao) {
    val listado: LiveData<List<AutoresEntity>> =
        dao.getAllRealData()

    suspend fun addLibro(autores: AutoresEntity) {
        dao.insert(autores)
    }

}