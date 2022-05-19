package com.example.libraryapproom.bd.repository

import androidx.lifecycle.LiveData
import com.example.libraryapproom.bd.dao.TypesDao
import com.example.libraryapproom.bd.entidades.TypesEntity

class TypesRepository(private val dao: TypesDao) {
    val listado: LiveData<List<TypesEntity>> =
    dao.getAllRealData()

    suspend fun addTypes(types:  TypesEntity){
        dao.insert(types)
    }
}