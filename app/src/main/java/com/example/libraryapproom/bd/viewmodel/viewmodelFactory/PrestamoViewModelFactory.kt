package com.example.libraryapproom.bd.viewmodel.viewmodelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.libraryapproom.bd.entidades.PrestamosEntity
import com.example.libraryapproom.bd.viewmodel.LibrosViewModel
import com.example.libraryapproom.bd.viewmodel.PrestamoViewModel

class PrestamoViewModelFactory(
    val app: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PrestamoViewModel(app) as T
    }
}