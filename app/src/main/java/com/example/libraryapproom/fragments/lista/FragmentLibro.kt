package com.example.libraryapproom.fragments.lista

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapproom.R
import com.example.libraryapproom.api.ApiService
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.bd.viewmodel.LibrosViewModel
import com.example.libraryapproom.databinding.FragmentLibroBinding
import com.example.libraryapproom.fragments.adapter.LibrosAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception


class FragmentLibro : Fragment() {
    lateinit var fBinding: FragmentLibroBinding
    private lateinit var viewModel : LibrosViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fBinding = FragmentLibroBinding.inflate(layoutInflater)
        val adapter = LibrosAdapter()
        val recycleView = fBinding.RcvLibro
        recycleView.adapter = adapter
        recycleView.layoutManager =
            LinearLayoutManager(requireContext())
        viewModel =
            ViewModelProvider(this).get(LibrosViewModel::class.java)
        viewModel.lista.observe(viewLifecycleOwner, Observer
        {libro->
            adapter.setData(libro)
        })
        //Agregar el menu
        setHasOptionsMenu(true)
        searchByID()

        return fBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState:
    Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()

    }


    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("http://192.168.56.1:9091/books/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByID() {



        CoroutineScope(Dispatchers.IO).launch {

            val call = getRetrofit().create(ApiService::class.java).getBook("all")

            val books = call.body()


            try {
                if(call.isSuccessful) {
                    val nombreLibro = books?.name.toString()
                    val Autor = books?.author?.name.toString() + books?.author?.surname.toString()
                    val genero = books?.typeId.toString()
                    val paginas = books?.pageCount.toString()


                    val libro = LibrosModels(0, nombreLibro, Autor, genero, paginas)
                    viewModel.agregarLibro(libro)


                }else {

                }


            } catch (ex: Exception ){
                val msg = Toast.makeText(activity,"Error de conexion + $ex",Toast.LENGTH_LONG)
                msg.setGravity(Gravity.CENTER, 0,0)
                msg.show()
            }


        }


    }


    //    private fun guardarLibros(){
//
//        CoroutineScope(Dispatchers.Main).launch {
//            try{
//
//                val call=getRetrofit().create(ApiService::class.java).getBook("1")
//
//                if(call.isSuccessful){
//                    val nombre = call.body()?.name.toString()
//                    val autor = call.body()?.author.toString()
//                    val genero = call.body()?.typeId.toString()
//                    val paginas = call.body()?.pageCount.toString()
//                    val libro = LibrosModels(0, nombre, autor, genero, paginas)
//                    viewModel.agregarLibro(libro)
//                }else{
//                    Toast.makeText(activity,"No se ha encontrado un registro",Toast.LENGTH_SHORT).show()
//                }
//            }catch (ex:Exception){
//                val msg = Toast.makeText(activity,"Error de conexion",Toast.LENGTH_LONG)
//                msg.setGravity(Gravity.CENTER, 0,0)
//                msg.show()
//            }
//        }
//    }
    private fun setupViews() {
        with(fBinding) {
            BtnAgregar.setOnClickListener {

                it.findNavController().navigate(R.id.action_global_fragmentAddlibro)
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater:
    MenuInflater
    ) {
        inflater.inflate(R.menu.delete_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if (item.itemId == R.id.mnuEliminar) {
            eliminarTodo()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun eliminarTodo() {
        val alerta = AlertDialog.Builder(requireContext())
        alerta.setPositiveButton("Si") { _, _ ->
            viewModel.eliminarTodo()
            Toast.makeText(
                requireContext(),
                "Registros eliminados satisfactoriamente...",
                Toast.LENGTH_LONG
            ).show()
        }
        alerta.setNegativeButton("No") { _, _ ->
            Toast.makeText(
                requireContext(),
                "Operación cancelada...",
                Toast.LENGTH_LONG
            ).show()
        }
        alerta.setTitle("Eliminando todos los registro")
        alerta.setMessage("¿Esta seguro de eliminar los registros?")
        alerta.create().show()
    }

}
