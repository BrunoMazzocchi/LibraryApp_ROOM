package com.example.libraryapproom.fragments.agregar


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.libraryapproom.R
import com.example.libraryapproom.api.ApiService
import com.example.libraryapproom.api.dataClass.Author
import com.example.libraryapproom.api.dataClass.Books
import com.example.libraryapproom.api.dataClass.Type
import com.example.libraryapproom.api.network.Common
import com.example.libraryapproom.api.network.NetworkConnection
import com.example.libraryapproom.bd.dao.AutoresDao
import com.example.libraryapproom.bd.dao.LibrosDao
import com.example.libraryapproom.bd.dao.MainBaseDatos
import com.example.libraryapproom.bd.dao.TypesDao
import com.example.libraryapproom.bd.entidades.AuthorsEntity
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.bd.entidades.TypesEntity
import com.example.libraryapproom.bd.viewmodel.LibrosViewModel
import com.example.libraryapproom.databinding.FragmentAddlibroBinding
import kotlinx.android.synthetic.main.fragment_actualizar_libro.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class FragmentAddlibro: Fragment() {
    lateinit var mService: ApiService
    lateinit var fBinding: FragmentAddlibroBinding
    private lateinit var viewModel: LibrosViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        fBinding = FragmentAddlibroBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(LibrosViewModel::class.java)
        checkInternet()
        fBinding.btnGuardar.setOnClickListener {
            guardarRegistroOffline()

        }

        mService = Common.retrofitService
        initSpinner(requireContext())
        return fBinding.root
    }





    private fun initSpinner(context: Context){
        val db: MainBaseDatos = MainBaseDatos.getDataBase(context)
        val daoA: AutoresDao = db.autoresDao()
        val daoT: TypesDao = db.typesDao()

        //ArrayList de los spinners
        var arrayAuthor:ArrayList<String> = arrayListOf("Autores..")
        var arrayType:ArrayList<String> = arrayListOf("Genero..")

        CoroutineScope(Dispatchers.Main).launch {
            var listaAutor: List<AuthorsEntity> = daoA.getAllAuthors()
            val listaType: List<TypesEntity> = daoT.getAllTypes()

            //Llenando spinners
            listaAutor.forEach {
                arrayAuthor.add(it.name)
            }

            listaType.forEach {
                arrayType.add(it.name)
            }
        }

        val adapterAutor: ArrayAdapter<String> = ArrayAdapter<String>(context,R.layout.spinner_item, arrayAuthor)
        fBinding.spAutor.adapter = adapterAutor

        val adapterType: ArrayAdapter<String> = ArrayAdapter<String>(context,R.layout.spinner_item, arrayType)
        fBinding.spType.adapter = adapterType

    }

    private fun guardarRegistro() {
        val nombre = fBinding.txtNombre.text.toString()
        val Paginas = fBinding.txtPaginas.text.toString()
        var authorID = fBinding.spAutor.selectedItemPosition.toString().toInt()
        var typeID = fBinding.spType.selectedItemPosition.toString().toInt()
        var point = 0
        var id: Int = 0

        val jsonObject = JSONObject()
        jsonObject.put("bookId", id)
        jsonObject.put("name", "$nombre")
        jsonObject.put("pageCount", Paginas.toInt())
        jsonObject.put("point", point)
        jsonObject.put("authorId", authorID)
        jsonObject.put("typeId", typeID)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val db: MainBaseDatos = MainBaseDatos.getDataBase(requireContext().applicationContext)
        val daoB: LibrosDao = db.librosDao()
        CoroutineScope(Dispatchers.IO).launch {
            mService.addABook(requestBody)

            val idType = daoB.getByStringType(typeID)
            val idAuthor = daoB.getByStringAutores(authorID)

            var book = LibrosModels(0, nombre, Paginas, authorID, typeID, 100)

            viewModel.agregarLibro(book)
        }


        Toast.makeText(
            requireContext(), "Registro actualizado",
            Toast.LENGTH_LONG
        ).show()


        findNavController().navigate(R.id.ir_a_listalibro)
    }
    var noInternetArray = mutableListOf<LibrosModels>()

    fun checkInternet(){

        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {

                val db: MainBaseDatos = MainBaseDatos.getDataBase(requireContext().applicationContext)
                val daoB: LibrosDao = db.librosDao()


                CoroutineScope(Dispatchers.IO).launch {
                    var libros: List<LibrosModels> = daoB.getAll()
                    var librosRet= mService.getAllBooks()
                    var librosSize = libros.size
                    var librosRetSize = librosRet.size

                    if( librosSize > librosRetSize ){
                        for(i in 0..libros.lastIndex){
                            var id: Int = libros[i].ID
                            var nombre: String = libros[i].nombreLibro
                            var paginas: String = libros[i].Paginas
                            var point: Int = libros[i].point?.toInt()!!
                            var authorID: Int = libros[i].authorID!!
                            var typeID: Int = libros[i].typeID!!


                            val jsonObject = JSONObject()
                            jsonObject.put("bookId", id)
                            jsonObject.put("name", "$nombre")
                            jsonObject.put("pageCount", paginas.toInt())
                            jsonObject.put("point", point)
                            jsonObject.put("authorId", authorID)
                            jsonObject.put("typeId", typeID)


                            val jsonObjectString = jsonObject.toString()
                            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
                            println("Hola antes de enviar")

                            CoroutineScope(Dispatchers.IO).launch {
                                mService.addABook(requestBody)

                                println("Hola enviado")
                            }
                        }


                    } else if ( librosSize == librosRetSize ) {
                        Toast.makeText(
                            requireContext(),
                            "Nada que hacer",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }











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


    private fun guardarRegistroOffline() {
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                guardarRegistro()
                return@observe
            }
            else {

                val nombre = fBinding.txtNombre.text.toString()
                val Paginas = fBinding.txtPaginas.text.toString()
                var authorID = fBinding.spAutor.selectedItemPosition.toString().toInt()
                var typeID = fBinding.spType.selectedItemPosition.toString().toInt()
                var point = 0
                var id: Int = 0


                val db: MainBaseDatos = MainBaseDatos.getDataBase(requireContext().applicationContext)
                val daoB: LibrosDao = db.librosDao()
                var book = LibrosModels(0, nombre, Paginas, authorID, typeID, 100)
                CoroutineScope(Dispatchers.IO).launch {

                    println("Hola")

                    val idType = daoB.getByStringType(typeID)
                    val idAuthor = daoB.getByStringAutores(authorID)


                    viewModel.agregarLibro(book)
                }

                noInternetArray.add(book)


                Toast.makeText(
                    requireContext(), "Guardado localmente ${noInternetArray.size}",
                    Toast.LENGTH_LONG
                ).show()


                findNavController().navigate(R.id.ir_a_listalibro)


                Toast.makeText(
                    requireContext(),
                    "No internet connection",
                    Toast.LENGTH_LONG
                ).show()
            }

        }



    }


}

