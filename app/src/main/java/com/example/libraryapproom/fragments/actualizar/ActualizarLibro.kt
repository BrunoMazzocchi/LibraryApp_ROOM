package com.example.libraryapproom.fragments.actualizar

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.libraryapproom.R
import com.example.libraryapproom.api.ApiService
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.bd.viewmodel.LibrosViewModel
import com.example.libraryapproom.databinding.FragmentActualizarLibroBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ActualizarLibro: Fragment()  {
    lateinit var fBinding: FragmentActualizarLibroBinding
    private val args by navArgs<ActualizarLibroArgs>()
    private lateinit var viewModel: LibrosViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fBinding = FragmentActualizarLibroBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(LibrosViewModel::class.java)
        with(fBinding) {

            txtNombre.setText(args.currentLibro.nombreLibro)
            txtAutor.setText(args.currentLibro.Autor)
            txtGenero.setText(args.currentLibro.genero)
            txtPaginas.setText(args.currentLibro.Paginas)

            btnGuardar.setOnClickListener {
                GuardarCambios()
            }
        }
        //Agregar menu
        setHasOptionsMenu(true)
        return fBinding.root
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("http://192.168.56.1:9091/books/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun deleteBook(ID: Int){
        CoroutineScope(Dispatchers.IO).launch {

            getRetrofit().create(ApiService::class.java).deleteBook(ID)

        }

    }
    private fun GuardarCambios() {
        val nombre = fBinding.txtNombre.text.toString()
        val Autor = fBinding.txtAutor.text.toString()
        val Genero = fBinding.txtGenero.text.toString()
        val Paginas = fBinding.txtPaginas.text.toString()

        //Crear el objeto
        val libro =
            LibrosModels(args.currentLibro.ID,
                nombre, Autor, Genero, Paginas )
        //Actualizar
        viewModel.actualizarLibro(libro)
        Toast.makeText(requireContext(), "Registro actualizado",
            Toast.LENGTH_LONG).show()


        findNavController().navigate(R.id.ir_a_listalibro)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater:
    MenuInflater
    ) {
        inflater.inflate(R.menu.delete_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if (item.itemId == R.id.mnuEliminar) {
            eliminarClasificacion()
        }
        return super.onOptionsItemSelected(item)
    }

    //Hacer refactor y renombrar correctamente
    private fun eliminarClasificacion() {
        val alerta = AlertDialog.Builder(requireContext())
        alerta.setPositiveButton("Si") { _, _ ->

            viewModel.eliminarLibro(args.currentLibro)
            deleteBook(args.currentLibro.ID)
            Toast.makeText(
                requireContext(),
                "Registro eliminado satisfactoriamente...  ${args.currentLibro.ID}",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.ir_a_listalibro)
        }
        alerta.setNegativeButton("No") { _, _ ->
            Toast.makeText(
                requireContext(),
                "Operación cancelada...",
                Toast.LENGTH_LONG
            ).show()
        }
        alerta.setTitle("Eliminando${args.currentLibro.nombreLibro}")
        alerta.setMessage("¿Esta seguro de eliminar a ${args.currentLibro.nombreLibro}?")
        alerta.create().show()
    }
}