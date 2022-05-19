package com.example.libraryapproom.fragments.lista

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapproom.R
import com.example.libraryapproom.api.ApiService
import com.example.libraryapproom.api.dataClass.Books
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


class FragmentLibro : Fragment() {
    lateinit var fBinding: FragmentLibroBinding
    private lateinit var viewModel: LibrosViewModel
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
        { libro ->
            adapter.setData(libro)
        })
        //Agregar el menu
        setHasOptionsMenu(true)
        // searchByID(2)

        return fBinding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState:
        Bundle?
    ) {
        searchAllBooks()
        super.onViewCreated(view, savedInstanceState)
        setupViews()

    }


    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("http://192.168.0.15:9091/books/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByID(ID: Int) {


        CoroutineScope(Dispatchers.IO).launch {

            val call = getRetrofit().create(ApiService::class.java).getBook("$ID")

            val books = call.body()


            try {
                if (call.isSuccessful) {
                    val nombreLibro = books?.name.toString()
                    val Autor = books?.author?.name.toString() + books?.author?.surname.toString()
                    val genero = books?.typeId.toString()
                    val paginas = books?.pageCount.toString()
                    var autorID = books?.authorId
                    var typeID = books?.typeId
                    var point = books?.point

                    val libro =
                        LibrosModels(0, nombreLibro, Autor, genero, paginas, autorID, typeID, point)
                    viewModel.agregarLibro(libro)


                } else {

                }


            } catch (ex: Exception) {
                val msg = Toast.makeText(activity, "Error de conexion + $ex", Toast.LENGTH_LONG)
                msg.setGravity(Gravity.CENTER, 0, 0)
                msg.show()
            }


        }


    }

    private fun searchByID(name: String) {


        CoroutineScope(Dispatchers.IO).launch {

            val call = getRetrofit().create(ApiService::class.java).getBook("$name")

            val books = call.body()


            try {
                if (call.isSuccessful) {
                    val nombreLibro = books?.name.toString()
                    val Autor = books?.author?.name.toString() + books?.author?.surname.toString()
                    val genero = books?.typeId.toString()
                    val paginas = books?.pageCount.toString()
                    var autorID = books?.authorId
                    var typeID = books?.typeId
                    var point = books?.point

                    val libro =
                        LibrosModels(0, nombreLibro, Autor, genero, paginas, autorID, typeID, point)
                    viewModel.agregarLibro(libro)


                } else {

                }


            } catch (ex: Exception) {
                val msg = Toast.makeText(activity, "Error de conexion + $ex", Toast.LENGTH_LONG)
                msg.setGravity(Gravity.CENTER, 0, 0)
                msg.show()
            }


        }


    }


    private fun searchAllBooks() {
        var list: ArrayList<Books>

        CoroutineScope(Dispatchers.IO).launch {
            var count: Int = 0
            val call = getRetrofit().create(ApiService::class.java).getAllBooks()
            list = call

            list.forEach { _ ->
                run {
                    for (i in 0..list.lastIndex) {
                        var nombre: String = list[i].name.toString()
                        val autor: String = list[i].author?.name.toString() + " " + list[i].author?.surname.toString()
                        val genero: String = list[i].type?.name.toString()
                        val paginas: String = list[i].pageCount.toString()
                        val id: Int = (list[i].bookId?.toInt() ?: Int) as Int
                        var autorID = (list[i].authorId)
                        var typeID = (list[i].typeId)
                        var point = (list[i].point)
                        val libro =
                            LibrosModels(id, nombre, autor, genero, paginas, autorID, typeID, point)
                        count++



                        viewModel.agregarLibro(libro)

                    }
                }

                if (count == list.size) {
                    return@launch
                }

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

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_bar, menu)
    }*/

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.BtnBusqueda){

        }

        return super.onOptionsItemSelected(item)
    }*/



    /* Eliminar todo */
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
