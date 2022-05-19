package com.example.libraryapproom.bd.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.libraryapproom.bd.dao.MainBaseDatos
import com.example.libraryapproom.bd.entidades.TypesEntity
import com.example.libraryapproom.bd.repository.TypesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class typesViewModel(application: Application): AndroidViewModel(application) {
    val listaT: LiveData<List<TypesEntity>>
    private val repository: TypesRepository

    init {
        val typesDao = MainBaseDatos.getDataBase(application).typesDao()
        repository = TypesRepository(typesDao)
        listaT = repository.listado
    }

    fun agregarTypes(types: TypesEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.addTypes(types)
        }

    }
}