package com.example.libraryapproom.bd.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.libraryapproom.bd.dao.MainBaseDatos
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.bd.entidades.TypesEntity
import com.example.libraryapproom.bd.entidades.AuthorsEntity
import com.example.libraryapproom.bd.entidades.vistas.view_books
import com.example.libraryapproom.bd.repository.LibrosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import kotlin.coroutines.coroutineContext

class LibrosViewModel (application: Application): AndroidViewModel(application) {
    val lista : LiveData<List<view_books>>
    lateinit var listaType: List<TypesEntity>
    var listaA: List<AuthorsEntity> = emptyList()



    private val repository: LibrosRepository
    init {
        val librosDao = MainBaseDatos.getDataBase(application).librosDao()
        val typesDao = MainBaseDatos.getDataBase(application).typesDao()
        val autores = MainBaseDatos.getDataBase(application).autoresDao()

        repository = LibrosRepository(librosDao, typesDao, autores)
        lista = repository.listadoAll

        viewModelScope.launch(Dispatchers.IO){
            listaA = repository.authors()
        }


    }

    fun initType(): List<TypesEntity>{
        CoroutineScope(Dispatchers.Main).launch {
            listaType = repository.types()
        }

        return listaType
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