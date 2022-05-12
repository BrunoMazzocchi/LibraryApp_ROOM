package com.example.libraryapproom.fragments.agregar


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.libraryapproom.R
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.bd.viewmodel.LibrosViewModel
import com.example.libraryapproom.databinding.FragmentAddlibroBinding

class FragmentAddlibro: Fragment() {
    lateinit var fBinding: FragmentAddlibroBinding
    private lateinit var viewModel: LibrosViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        fBinding = FragmentAddlibroBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(LibrosViewModel::class.java)
        fBinding.btnGuardar.setOnClickListener {
            guardarRegistro()
        }
        fBinding.txtAutor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        return fBinding.root
    }
    private fun guardarRegistro() {
        //val baseDatos = MainBaseDatos.getDataBase(this)
        val nombre = fBinding.txtNombre.text.toString()
        val autor = fBinding.txtAutor.selectedItem.toString()
        val genero = fBinding.txtGenero.text.toString()
        val paginas = fBinding.txtPaginas.text.toString()
        //Crear objeto
        val libro = LibrosModels(0, nombre, autor, genero, paginas)
        //Agregar nuevo usuario
        viewModel.agregarLibro(libro)
        Toast.makeText(requireContext(), "Registro guardado",
            Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.ir_a_listalibro)
    }

}

