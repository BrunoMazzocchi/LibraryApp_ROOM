package com.example.libraryapproom.bd.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.libraryapproom.bd.dao.MainBaseDatos
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.bd.repository.LibrosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class LibrosViewModel (application: Application): AndroidViewModel(application) {
    val lista : LiveData<List<LibrosModels>>
    private val repository: LibrosRepository
    init {
        val librosDao =
            MainBaseDatos.getDataBase(application).librosDao()
        repository = LibrosRepository(librosDao)
        lista = repository.listado
    }
    fun agregarLibro(libros: LibrosModels){
        viewModelScope.launch(Dispatchers.IO) {
                repository.addLibro(libros)
        }
    }
    fun actualizarLibro(libros: LibrosModels){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateLibro(libros)
        }
    }
    fun eliminarLibro(libros: LibrosModels){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteLibro(libros)
        }
    }
    fun eliminarTodo(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun agregarTodosLosLibros(){

    }
}