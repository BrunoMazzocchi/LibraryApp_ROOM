package com.example.libraryapproom.fragments.lista

import android.app.AlertDialog
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.*

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapproom.MainActivity
import com.example.libraryapproom.R
import com.example.libraryapproom.api.ApiService
import com.example.libraryapproom.api.dataClass.Author
import com.example.libraryapproom.api.dataClass.Books
import com.example.libraryapproom.api.dataClass.Type
import com.example.libraryapproom.api.network.Common
import com.example.libraryapproom.api.network.NetworkConnection
import com.example.libraryapproom.bd.entidades.AuthorsEntity
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.bd.entidades.TypesEntity
import com.example.libraryapproom.bd.viewmodel.AutoresViewModel
import com.example.libraryapproom.bd.viewmodel.LibrosViewModel
import com.example.libraryapproom.bd.viewmodel.TypesViewModel
import com.example.libraryapproom.databinding.FragmentLibroBinding
import com.example.libraryapproom.fragments.adapter.LibrosAdapter
import com.example.libraryapproom.fragments.agregar.FragmentAddlibro
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList


class FragmentLibro : Fragment() {
    lateinit var fBinding: FragmentLibroBinding
    private lateinit var viewModel: LibrosViewModel
    private lateinit var viewModelAuthor: AutoresViewModel
    private lateinit var viewModelType: TypesViewModel
    private lateinit var mService: ApiService
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fBinding = FragmentLibroBinding.inflate(layoutInflater)
        val adapter = LibrosAdapter()
        val ran: IntRange = 0..254
        val recycleView = fBinding.RcvLibro
        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(requireContext())

        //Inicializando ViewModels

        //TypesViewModels

        viewModelType = ViewModelProvider(this).get(TypesViewModel::class.java)

        //AuthorsViewModels
        viewModelAuthor = ViewModelProvider(this).get(AutoresViewModel::class.java)

        //BooksViewModel
        viewModel = ViewModelProvider(this).get(LibrosViewModel::class.java)

        //Llenando ReclyclerView


        viewModel.lista.observe(viewLifecycleOwner, Observer
        { libro ->
            adapter.setData(libro)
        })
        //Agregar el menu
        // searchByID(2)


        mService = Common.retrofitService
        return fBinding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkInternet()
        setupViews()

    }

    private fun checkInternet(){

        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                searchAllBooks()
                searchAllAuthors()
                searchAllTypes()
            }
            else {
                // Show No internet connection message
                Toast.makeText(
                    requireContext(),
                    "No internet connection",
                    Toast.LENGTH_LONG
                ).show()
            }

            }


    }

    private fun searchByID(ID: Int) {


        CoroutineScope(Dispatchers.IO).launch {

            val call = mService.getBook("$ID")

            val books = call.body()


            try {
                if (call.isSuccessful) {
                    val nombreLibro = books?.name.toString()
                    val paginas = books?.pageCount.toString()
                    var autorID = books?.authorId
                    var typeID = books?.typeId
                    var point = books?.point

                    val libro =
                        LibrosModels(0, nombreLibro, paginas, autorID, typeID, point)
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

            val call = mService.getBook("$name")

            val books = call.body()


            try {
                if (call.isSuccessful) {
                    val nombreLibro = books?.name.toString()
                    val paginas = books?.pageCount.toString()
                    var point = books?.point

                    var autorID = books?.authorId
                    var typeID = books?.typeId

                    val libro =
                        LibrosModels(0, nombreLibro, paginas, point, autorID, typeID)
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
            val call = mService.getAllBooks()
            list = call

            list.forEach { _ ->
                run {
                    for (i in 0..list.lastIndex) {
                        var nombre: String = list[i].name.toString()
                        val paginas: String = list[i].pageCount.toString()
                        val id: Int = (list[i].bookId?.toInt() ?: Int) as Int
                        var autorID = (list[i].authorId)
                        var typeID = (list[i].typeId)
                        var point = (list[i].point)
                        val libro =
                            LibrosModels(id, nombre, paginas, autorID, typeID, point)
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


    private fun searchAllAuthors() {
        var lista: ArrayList<Author>

        CoroutineScope(Dispatchers.IO).launch {
            var count: Int = 0
            val call = mService.getAllAuthors()
            lista = call

            lista.forEach { _ ->
                run {
                    for (i in 0..lista.lastIndex) {
                        val idAuthor: Int = lista[i].authorId.toString().toInt()
                        var name: String = lista[i].name.toString()
                        val surname: String = lista[i].surname.toString()
                        val author = AuthorsEntity(idAuthor, name, surname)
                        count++

                        viewModelAuthor.agregarAutores(author)

                    }
                }

                if (count == lista.size) {
                    return@launch
                }

            }
        }


    }

    private fun searchAllTypes() {
        var lista: ArrayList<Type>

        CoroutineScope(Dispatchers.IO).launch {
            var count: Int = 0
            val call = mService.getAllType()
            lista = call

            lista.forEach { _ ->
                run {
                    for (i in 0..lista.lastIndex) {
                        val type_id: Int = lista[i].typeId.toString().toInt()
                        var name: String = lista[i].name.toString()
                        val type = TypesEntity(type_id, name)
                        count++

                        viewModelType.agregarTypes(type)

                    }
                }

                if (count == lista.size) {
                    return@launch
                }

            }
        }


    }


    private fun setupViews() {
        with(fBinding) {
            BtnAgregar.setOnClickListener {

                it.findNavController().navigate(R.id.action_global_fragmentAddlibro)
            }

        }
    }
    //Cambiando Titulos de action bar
    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.title = "Libros"
    }

    /* Eliminar todo */
    //No funcional
    /*private fun eliminarTodo() {
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
    }*/
}


