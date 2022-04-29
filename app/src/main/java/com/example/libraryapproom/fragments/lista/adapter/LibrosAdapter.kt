package com.example.libraryapproom.fragments.lista.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapproom.R
import com.example.libraryapproom.bd.entidades.LibrosModels

class LibrosAdapter( private val librosList:List<LibrosModels>) : RecyclerView.Adapter<LibrosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibrosViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return LibrosViewHolder(layoutInflater.inflate(R.layout.lista_libro, parent, false))
    }

    override fun onBindViewHolder(holder: LibrosViewHolder, position: Int) {
        val item = librosList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = librosList.size
}