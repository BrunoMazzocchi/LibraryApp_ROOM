package com.example.libraryapproom.fragments.actualizar

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.libraryapproom.R
import com.example.libraryapproom.api.ApiService
import com.example.libraryapproom.api.dataClass.Author
import com.example.libraryapproom.api.dataClass.Type
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.bd.viewmodel.LibrosViewModel
import com.example.libraryapproom.databinding.FragmentActualizarLibroBinding
import kotlinx.android.synthetic.main.fragment_actualizar_libro.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActualizarLibro : Fragment() {
    lateinit var fBinding: FragmentActualizarLibroBinding
    private val args by navArgs<ActualizarLibroArgs>()
    private lateinit var viewModel: LibrosViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fBinding = FragmentActualizarLibroBinding.inflate(layoutInflater)
        fBinding = FragmentActualizarLibroBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(LibrosViewModel::class.java)

        with(fBinding) {

            populateSpinner()
            populateSpinnerType()

            txtNombre.setText(args.currentLibro.nombreLibro)
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
            .baseUrl("http://192.168.56.1:9091/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun populateSpinner() {
        CoroutineScope(Dispatchers.IO).launch {
            var authorArray: MutableList<Author>
            val call = getRetrofit().create(ApiService::class.java).getAllAuthors()
            authorArray = call
            var count: Int = 0

            var authorArrayFinal = mutableListOf<String>()
            authorArray.forEach { _ ->

                run {
                    for (i in 0..authorArray.lastIndex) {
                        var autorID: Int? = authorArray[i].authorId
                        var nombre: String = authorArray[i].name.toString()
                        var surname: String = authorArray[i].surname.toString()

                        var author: String = " $autorID - $nombre -  $surname"

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
                    var id = args.currentLibro.authorID


                    if (id != null) {
                        listView.setSelection(id - 1)
                    }

                    return@launch
                }


            }


        }
    }
    private fun populateSpinnerType() {
        CoroutineScope(Dispatchers.IO).launch {
            var typeArray: MutableList<Type>
            val call = getRetrofit().create(ApiService::class.java).getAllGenre()
            typeArray = call
            var count: Int = 0

            var typeArrayFinal = mutableListOf<String>()
            typeArray.forEach { _ ->

                run {
                    for (i in 0..typeArray.lastIndex) {
                        var typeID: Int? = typeArray[i].typeId
                        var nombre: String = typeArray[i].name.toString()

                        var genero: String = " $typeID - $nombre"

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
                    var id = args.currentLibro.typeID


                    if (id != null) {
                        listView.setSelection(id - 1)
                    }

                    return@launch
                }


            }


        }
    }

    private fun deleteBook(ID: Int) {
        CoroutineScope(Dispatchers.IO).launch {

            getRetrofit().create(ApiService::class.java).deleteBook(ID)

        }

    }

    private fun GuardarCambios() {
        val nombre = fBinding.txtNombre.text.toString()
        val Autor = fBinding.txtAutor
        val Genero = ""
        val Paginas = fBinding.txtPaginas.text.toString()
        var authorID = txtAutor.selectedItemPosition + 1
        var generoID = txtGenero.selectedItemPosition + 1
        var point = args.currentLibro.point
        var id: Int = args.currentLibro.ID

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
            getRetrofit().create(ApiService::class.java).editABook(requestBody)


        }

        var book =
            LibrosModels(id, nombre, Autor.toString(), Genero.toString(), Paginas, point, authorID, generoID)
        //Crear el objeto
        val libro =
            LibrosModels(
                args.currentLibro.ID,
                nombre, Autor.toString(), Genero.toString(), Paginas, authorID, generoID, point
            )
        //Actualizar
        viewModel.actualizarLibro(libro)
        Toast.makeText(
            requireContext(), "Registro actualizado",
            Toast.LENGTH_LONG
        ).show()


        findNavController().navigate(R.id.ir_a_listalibro)
    }

    override fun onCreateOptionsMenu(
        menu: Menu, inflater:
        MenuInflater
    ) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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


