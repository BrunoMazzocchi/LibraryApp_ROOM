package com.example.libraryapproom.fragments.agregar


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
import com.example.libraryapproom.api.dataClass.Type
import com.example.libraryapproom.api.network.Common
import com.example.libraryapproom.api.network.NetworkConnection
import com.example.libraryapproom.bd.entidades.LibrosModels
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
        fBinding.btnGuardar.setOnClickListener {
            guardarRegistro()
        }
        mService = Common.retrofitService

        checkInternet()
        return fBinding.root
    }


    private fun checkInternet(){

        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                populateSpinner()
                populateSpinnerType()

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

    private fun populateSpinner() {
        CoroutineScope(Dispatchers.IO).launch {
            var authorArray: MutableList<Author>
            val call = mService.getAllAuthors()
            authorArray = call
            var count: Int = 0
            var authorArrayFinal = mutableListOf<String>()
            authorArray.forEach { _ ->

                run {
                    for (i in 0..authorArray.lastIndex) {
                        var autorID: Int? = authorArray[i].authorId
                        var nombre: String = authorArray[i].name.toString()
                        var surname: String = authorArray[i].surname.toString()

                        var author = " $autorID - $nombre -  $surname"

                        authorArrayFinal.add(author)

                        count++

                    }
                }
                if (count == authorArray.size) {

                    var listView = fBinding.txtAutor

                    var arrayAdapter = activity?.let {
                        ArrayAdapter(
                            it,
                            android.R.layout.simple_list_item_1,
                            authorArrayFinal
                        )
                    }
                    listView.adapter = arrayAdapter
                    return@launch
                }


            }


        }
    }

    private fun populateSpinnerType() {
        CoroutineScope(Dispatchers.IO).launch {
            var typeArray: MutableList<Type>

            val call = mService.getAllType()
            typeArray = call
            var count: Int = 0

            var typeArrayFinal = mutableListOf<String>()
            typeArray.forEach { _ ->

                run {
                    for (i in 0..typeArray.lastIndex) {
                        var typeID: Int? = typeArray[i].typeId
                        var nombre: String = typeArray[i].name.toString()
                        var genero = " $typeID - $nombre"

                        typeArrayFinal.add(genero)

                        count++

                    }
                }
                if (count == typeArrayFinal.size) {

                    var listView = fBinding.txtGenero

                    var arrayAdapter = activity?.let {
                        ArrayAdapter(
                            it,
                            android.R.layout.simple_list_item_1,
                            typeArrayFinal
                        )
                    }
                    listView.adapter = arrayAdapter

                    return@launch
                }


            }


        }
    }





    private fun guardarRegistro() {
        val nombre = fBinding.txtNombre.text.toString()
        val Paginas = fBinding.txtPaginas.text.toString()
        var authorID = txtAutor.selectedItemPosition + 1
        var generoID = txtGenero.selectedItemPosition + 1
        var point = 0
        var id: Int = 0

        val jsonObject = JSONObject()
        jsonObject.put("bookId", id)
        jsonObject.put("name", "$nombre")
        jsonObject.put("pageCount", Paginas.toInt())
        jsonObject.put("point", point)
        jsonObject.put("authorId", authorID)
        jsonObject.put("typeId", generoID)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            mService.addABook(requestBody)


        }

        var book =
            LibrosModels(id, nombre, "", "", Paginas, authorID, generoID, point)

        //Actualizar
        viewModel.agregarLibro(book)
        Toast.makeText(
            requireContext(), "Registro actualizado",
            Toast.LENGTH_LONG
        ).show()


        findNavController().navigate(R.id.ir_a_listalibro)
    }

}

