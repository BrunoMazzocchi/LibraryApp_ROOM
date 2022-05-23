package com.example.libraryapproom.bd.repository

import androidx.lifecycle.LiveData
import com.example.libraryapproom.bd.dao.AutoresDao
import com.example.libraryapproom.bd.dao.EstudiantesDao
import com.example.libraryapproom.bd.entidades.AuthorsEntity
import com.example.libraryapproom.bd.entidades.EstudiantesEntity

class EstudiantesRepository(private val dao: EstudiantesDao) {

    val listado: LiveData<List<EstudiantesEntity>> =
        dao.getAllRealData()

    suspend fun addStudents(autors: EstudiantesEntity) {
        dao.insert(autors)
    }

}