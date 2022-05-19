package com.example.libraryapproom.fragments.agregar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.libraryapproom.R
import com.example.libraryapproom.bd.entidades.PrestamosEntity
import com.example.libraryapproom.bd.viewmodel.PrestamoViewModel
import com.example.libraryapproom.databinding.FragmentAddPrestamoBinding


class AddPrestamoFragment : Fragment() {
    private lateinit var aBinding: FragmentAddPrestamoBinding
    private lateinit var viewModel: PrestamoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aBinding = FragmentAddPrestamoBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(PrestamoViewModel::class.java)

        aBinding.btnGuardarP.setOnClickListener {
            //guardarRegistro()
        }
        return aBinding.root
    }

   /* private fun guardarRegistro() {

       val libro  = aBinding.TxtLibro.text.toString()
        val est = aBinding.TxtEstudiante.text.toString()
        val fechaR = aBinding.TxtFechaRetiro.text.toString()
        val fechaE = aBinding.TxtFechaEntrega.text.toString()

        val pres = PrestamosEntity(0, est, libro, fechaR, fechaE)

        viewModel.agregarPrestamo(pres)

        Toast.makeText(requireContext(), "Registro guardado",
            Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.listaPrestamo)
    }*/

}