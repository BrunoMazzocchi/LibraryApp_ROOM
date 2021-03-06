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
import com.example.libraryapproom.MainActivity
import com.example.libraryapproom.R
import com.example.libraryapproom.api.ApiService
import com.example.libraryapproom.api.network.Common
import com.example.libraryapproom.bd.dao.EstudiantesDao
import com.example.libraryapproom.bd.dao.LibrosDao
import com.example.libraryapproom.bd.dao.MainBaseDatos
import com.example.libraryapproom.bd.dao.PrestamosDao
import com.example.libraryapproom.bd.entidades.EstudiantesEntity
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.bd.entidades.PrestamosEntity
import com.example.libraryapproom.bd.viewmodel.PrestamoViewModel
import com.example.libraryapproom.databinding.FragmentActualizarPrestamoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class ActualizarPrestamoFragment : Fragment() {
    lateinit var mService: ApiService
    lateinit var uBinding: FragmentActualizarPrestamoBinding
    private val args by navArgs<ActualizarPrestamoFragmentArgs>()
    private lateinit var viewModel: PrestamoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        uBinding =
            FragmentActualizarPrestamoBinding.inflate(layoutInflater)
        viewModel =
            ViewModelProvider(this).get(PrestamoViewModel::class.java)

        var v: Int = 10

        initSpinnerEst(requireContext())
        initSpinnerLibro(requireContext())
        with(uBinding){
            mService = Common.retrofitService
            TxtFechaRetiro.setText(args.currentPrestamo.taken_date)
            TxtFechaEntrega.setText(args.currentPrestamo.brought_date)

            BtnEditarP.setOnClickListener {
                guardarCambios()
            }
        }
        setHasOptionsMenu(true)
        return uBinding.root
    }

    //Cambiando Titulos de action bar
    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.title = "Actualizar prestamo"
    }

    private fun initSpinnerEst(context: Context){
        val db: MainBaseDatos = MainBaseDatos.getDataBase(context)
        val daoEst: EstudiantesDao = db.estudiantesDao()

        //Arraylist para los spinners
        var arrayEst: ArrayList<String> = arrayListOf("Estudiantes...")

        CoroutineScope(Dispatchers.Main).launch {
            var listaEst: List<EstudiantesEntity> = daoEst.getAllStudents()

            //Lenan spinners

            listaEst.forEach{
                arrayEst.add(it.name)
            }

            var listView = uBinding.spEstudiante

            var arrayAdapter1 = activity?.let {
                ArrayAdapter(it, R.layout.spinner_item, arrayEst)
            }
            listView.adapter = arrayAdapter1

            //Espera de 0.5s para realizar el setSelection

            Handler().postDelayed({
                var id = args.currentPrestamo.student_id


                if (id != null) {
                    listView.setSelection(id)
                }
            }, 500)

        }
    }
    private fun initSpinnerLibro(context: Context){
        val db: MainBaseDatos = MainBaseDatos.getDataBase(context)
        val daoLib: LibrosDao = db.librosDao()

        //Arraylist para los spinners
        var arrayLib: ArrayList<String> = arrayListOf("Libros...")

        CoroutineScope(Dispatchers.Main).launch {
            var listaLibro: List<LibrosModels> = daoLib.getAll()

            //Lenan spinners

            listaLibro.forEach{
                arrayLib.add(it.nombreLibro)
            }

            var listView = uBinding.spLibro

            var arrayAdapter1 = activity?.let {
                ArrayAdapter(it, R.layout.spinner_item, arrayLib)
            }
            listView.adapter = arrayAdapter1

            //Espera de 0.5s para realizar el setSelection

            Handler().postDelayed({
                var id = args.currentPrestamo.book_id


                if (id != null) {
                    listView.setSelection(id)
                }
            }, 500)

        }
    }

    private fun guardarCambios() {
        val libro = uBinding.spLibro.selectedItemPosition.toString().toInt()
        val est = uBinding.spEstudiante.selectedItemPosition.toString().toInt()
        val fechaR = uBinding.TxtFechaRetiro.text.toString()
        val fechaE = uBinding.TxtFechaEntrega.text.toString()
        var id: Int = args.currentPrestamo.id

        val jsonObject = JSONObject()
        jsonObject.put("borrowId", id)
        jsonObject.put("studentId", est)
        jsonObject.put("bookId", libro)
        jsonObject.put("takenDate", fechaR)
        jsonObject.put("broughtDate", fechaE)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
                mService.editBorrow(requestBody)

        }
        //Crea Objeto
        val prestamo =
            PrestamosEntity(id, est, libro, fechaR, fechaE)
        //Envia objeto para actulizar registro de manera local
        viewModel.actualizarPrestamo(prestamo)
        Toast.makeText(requireContext(), "Registro actualizado",
            Toast.LENGTH_LONG).show()

        findNavController().navigate(R.id.listaPrestamo)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater:
    MenuInflater
    ) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        if (item.itemId == R.id.mnuEliminar) {
            eliminarPrestamo()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteBorrow(ID: Int){
        CoroutineScope(Dispatchers.IO).launch {
            mService.deleteBorrow(ID)
        }

    }

    private fun eliminarPrestamo() {
        val alerta = AlertDialog.Builder(requireContext())
        alerta.setPositiveButton("Si") { _, _ ->
            viewModel.eliminarPrestamo(args.currentPrestamo.id)
            deleteBorrow(args.currentPrestamo.id)
            Toast.makeText(
                requireContext(),
                "Registro eliminado satisfactoriamente...",
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.listaPrestamo)
        }
        alerta.setNegativeButton("No") { _, _ ->
            Toast.makeText(
                requireContext(),
                "Operaci??n cancelada...",
                Toast.LENGTH_LONG
            ).show()
        }
        alerta.setTitle("Eliminando prestamo realizado por ${args.currentPrestamo.student_name}")
        alerta.setMessage("??Esta seguro de eliminar prestamo realizado por ${args.currentPrestamo.student_name}?")
        alerta.create().show()
    }
}