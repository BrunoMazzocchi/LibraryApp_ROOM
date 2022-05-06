package com.example.libraryapproom.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapproom.R
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.databinding.ListaLibroBinding
import com.example.libraryapproom.fragments.lista.FragmentLibroDirections

class LibrosAdapter: RecyclerView.Adapter<LibrosAdapter.ClasificacionHolder>() {
    var listado : List<LibrosModels> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType:
    Int): ClasificacionHolder {
        val binding =
            ListaLibroBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false)
        return ClasificacionHolder(binding)
    }

    override fun onBindViewHolder(holder: ClasificacionHolder, position: Int) : Unit =
        holder.bind(listado[position])

    override fun getItemCount(): Int = listado.size
    fun setData(libro: List<LibrosModels>) {
        this.listado = libro
        notifyDataSetChanged()
    }

    inner class ClasificacionHolder(val binding: ListaLibroBinding)
        :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(libro: LibrosModels) {
            with(binding) {
                TvName.text = libro.ID.toString()
                TvAuthor.text = libro.Autor
                TvGenre.text = libro.genero
                TvPageCount.text = libro.Paginas.toString()

                ClFila.setOnClickListener {
                    val action =
                        FragmentLibroDirections.irAActualizarlibro(libro)
                    it.findNavController().navigate(action)
                }
            }
        }
    }
}