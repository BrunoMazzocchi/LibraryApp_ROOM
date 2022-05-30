package com.example.libraryapproom.bd.viewmodel.viewmodelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.libraryapproom.bd.viewmodel.LibrosViewModel

class LibrosViewModelFactory(
    val app: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LibrosViewModel(app) as T
    }
}