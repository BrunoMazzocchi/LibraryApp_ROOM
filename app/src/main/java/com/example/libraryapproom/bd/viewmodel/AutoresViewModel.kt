package com.example.libraryapproom.bd.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.libraryapproom.bd.dao.AutoresDao
import com.example.libraryapproom.bd.dao.MainBaseDatos
import com.example.libraryapproom.bd.entidades.AutoresEntity
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.bd.repository.AutoresRepository
import com.example.libraryapproom.bd.repository.LibrosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AutoresViewModel (application: Application): AndroidViewModel(application) {
    val lista : LiveData<List<AutoresEntity>>
    private val repository: AutoresRepository
    init {
        val autoresDao =
            MainBaseDatos.getDataBase(application).autoresDao()
        repository = AutoresRepository(autoresDao)
        lista = repository.listado
    }
    fun agregarAutor(autores: AutoresEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addLibro(autores)
        }
    }

}