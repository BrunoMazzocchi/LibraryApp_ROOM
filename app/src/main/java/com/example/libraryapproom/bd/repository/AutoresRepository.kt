package com.example.libraryapproom.bd.repository

import androidx.lifecycle.LiveData
import com.example.libraryapproom.bd.dao.AutoresDao
import com.example.libraryapproom.bd.entidades.AuthorsEntity


class AutoresRepository(private val dao: AutoresDao) {

    val listado: LiveData<List<AuthorsEntity>> =
        dao.getAllRealData()

    suspend fun addAutores(autors: AuthorsEntity) {
        dao.insert(autors)
    }

}