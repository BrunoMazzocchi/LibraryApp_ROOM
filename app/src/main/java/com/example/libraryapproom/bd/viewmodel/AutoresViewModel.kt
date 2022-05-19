package com.example.libraryapproom.bd.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.libraryapproom.bd.dao.MainBaseDatos
import com.example.libraryapproom.bd.entidades.AuthorsEntity
import com.example.libraryapproom.bd.repository.AutoresRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AutoresViewModel(application: Application): AndroidViewModel(application) {
    val listaA: LiveData<List<AuthorsEntity>>
    private val repository: AutoresRepository

    init {
        val autoresDao = MainBaseDatos.getDataBase(application).autoresDao()
        repository = AutoresRepository(autoresDao)
        listaA = repository.listado
    }

    fun agregarAutores(autores: AuthorsEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.addAutores(autores)
        }
    }
}