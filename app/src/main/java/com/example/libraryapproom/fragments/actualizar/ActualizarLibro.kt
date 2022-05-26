package com.example.libraryapproom.fragments.actualizar

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.libraryapproom.R
import com.example.libraryapproom.api.ApiService
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
import com.example.libraryapproom.databinding.FragmentActualizarLibroBinding
import kotlinx.android.synthetic.main.fragment_actualizar_libro.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class ActualizarLibro : Fragment() {
    lateinit var mService: ApiService
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
        spinnerAuthors(requireContext())
        spinnerType(requireContext())
        var v: Int = 10
        with(fBinding) {
            mService = Common.retrofitService
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

    private fun checkInternet() {

        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {


            } else {
                // Show No internet connection message
                Toast.makeText(
                    requireContext(),
                    "No internet connection",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }


    private fun deleteBook(ID: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            mService.deleteBook(ID)

        }

    }

    private fun GuardarCambios() {
        val nombre = fBinding.txtNombre.text.toString()
        val Paginas = fBinding.txtPaginas.text.toString()
        var authorID = fBinding.spAutor.selectedItemPosition.toString().toInt()
        var typeID = fBinding.spType.selectedItemPosition.toString().toInt()
        var point = args.currentLibro.point
        var id: Int = args.currentLibro.ID

        val jsonObject = JSONObject()
        jsonObject.put("bookId", id)
        jsonObject.put("name", "$nombre")
        jsonObject.put("pageCount", Paginas.toInt())
        jsonObject.put("point", point)
        jsonObject.put("authorId", authorID)
        jsonObject.put("typeId", typeID)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            mService.editABook(requestBody)


        }

        //Crear el objeto
        val libro =
            LibrosModels(args.currentLibro.ID, nombre, Paginas, authorID, typeID, point)
        //Actualizar
        viewModel.actualizarLibro(libro)
        Toast.makeText(
            requireContext(), "Registro actualizado", Toast.LENGTH_LONG
        ).show()


        findNavController().navigate(R.id.ir_a_listalibro)
    }

    //Spinners

    private fun spinnerAuthors(context: Context) {
        val db: MainBaseDatos = MainBaseDatos.getDataBase(context)
        val daoA: AutoresDao = db.autoresDao()
        //ArrayList de los spinners
        var arrayAuthor: ArrayList<String> = arrayListOf("Autores..")

        CoroutineScope(Dispatchers.Main).launch {
            var listaAutor: List<AuthorsEntity> = daoA.getAllAuthors()

            //Llenando spinners
            listaAutor.forEach {
                arrayAuthor.add(it.name)
            }

        }

        var listView = fBinding.spAutor

        var arrayAdapter1 = activity?.let {
            ArrayAdapter(it, R.layout.spinner_item, arrayAuthor)
        }
        listView.adapter = arrayAdapter1

        //Espera de 1s para realizar el setSelection

        Handler().postDelayed({
            var id = args.currentLibro.author_ID


            if (id != null) {
                listView.setSelection(id)
            }
        }, 1000)

    }

    fun spinnerType(context: Context) {
        val db: MainBaseDatos = MainBaseDatos.getDataBase(context)
        val daoT: TypesDao = db.typesDao()

        var arrayType: ArrayList<String> = arrayListOf("Genero..")

        CoroutineScope(Dispatchers.Main).launch {
            val listaType: List<TypesEntity> = daoT.getAllTypes()

            listaType.forEach {
                arrayType.add(it.name)
            }
        }

        var listView = fBinding.spType

        var arrayAdapter1 = activity?.let {
            ArrayAdapter(it, R.layout.spinner_item, arrayType)
        }
        listView.adapter = arrayAdapter1

        //Espera de 1s para realizar el setSelection
        Handler().postDelayed({
            listView.setSelection(args.currentLibro.Type_ID)
        }, 1000)
    }

    override fun onCreateOptionsMenu(
        menu: Menu, inflater:
        MenuInflater
    ) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuEliminar) {
            deleteBook()
        }
        return super.onOptionsItemSelected(item)
    }

    //Hacer refactor y renombrar correctamente
    private fun deleteBook() {
        val alerta = AlertDialog.Builder(requireContext())
        alerta.setPositiveButton("Si") { _, _ ->
            viewModel.eliminarLibro(args.currentLibro.ID)
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


