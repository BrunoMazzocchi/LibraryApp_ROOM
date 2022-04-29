package com.example.libraryapproom.fragments.lista.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.databinding.ListaLibroBinding
import com.squareup.picasso.Picasso

class LibrosViewHolder(view:View):RecyclerView.ViewHolder(view) {

    val binding = ListaLibroBinding.bind(view)

    fun render(Librosmodels: LibrosModels){

        binding.TvName.text = Librosmodels.nombreLibro
        Picasso.get().load(Librosmodels.photo).into(binding.IvCover)
        binding.TvAuthor.text = Librosmodels.Autor
        binding.TvGenre.text =  Librosmodels.genero
        binding.TvPageCount.text =  Librosmodels.Paginas.toString()
        binding.TvId.text = Librosmodels.ID.toString()
    }
}