package com.example.libraryapproom.bd.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.libraryapproom.bd.dao.MainBaseDatos
import com.example.libraryapproom.bd.entidades.AuthorsEntity
import com.example.libraryapproom.bd.entidades.EstudiantesEntity
import com.example.libraryapproom.bd.repository.AutoresRepository
import com.example.libraryapproom.bd.repository.EstudiantesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EstudiantesViewModel (application: Application): AndroidViewModel(application) {
    val listaE: LiveData<List<EstudiantesEntity>>
    private val repository: EstudiantesRepository

    init {
        val esudiantesDao = MainBaseDatos.getDataBase(application).estudiantesDao()
        repository = EstudiantesRepository(esudiantesDao)
        listaE = repository.listado
    }

    fun agregarEstudiante(estudiante: EstudiantesEntity){
        viewModelScope.launch(Dispatchers.IO){
            repository.addStudents(estudiante)
        }
    }
}