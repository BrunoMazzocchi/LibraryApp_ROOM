package com.example.libraryapproom.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapproom.bd.entidades.PrestamosEntity
import com.example.libraryapproom.databinding.ListaPrestamoBinding
import com.example.libraryapproom.fragments.lista.FragmentPrestamoDirections

class PrestamosAdapter:
RecyclerView.Adapter<PrestamosAdapter.PrestamosHolder>(){
    private var listadoPrestamo = emptyList<PrestamosEntity>()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PrestamosHolder {
        val binding = ListaPrestamoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PrestamosHolder(binding)
    }

    override fun onBindViewHolder(holder: PrestamosHolder, position: Int) {
        holder.bind(
            listadoPrestamo[position]
        )
    }

    override fun getItemCount(): Int = listadoPrestamo.size

    fun setData(prestamos: List<PrestamosEntity>){
        this.listadoPrestamo = prestamos
        notifyDataSetChanged()
    }

    inner class PrestamosHolder(val binding: ListaPrestamoBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(prestamo: PrestamosEntity){
            with(binding){
                TvId.text = prestamo.id.toString()
                TvNombreLibro.text = prestamo.book_name
                TvEstudiante.text = prestamo.student_name
                TvFechaRetiro.text = prestamo.taken_date
                TvFechaEntrega.text = prestamo.brought_date

                ClFilaPrestamo.setOnClickListener {
                    val action = FragmentPrestamoDirections.editarPrestamo(prestamo)
                    it.findNavController().navigate(action)
                }
            }
        }
    }
}