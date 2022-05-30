package com.example.libraryapproom.fragments.agregar

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.libraryapproom.MainActivity
import com.example.libraryapproom.R
import com.example.libraryapproom.api.ApiService
import com.example.libraryapproom.api.network.Common
import com.example.libraryapproom.api.network.NetworkConnection
import com.example.libraryapproom.bd.dao.EstudiantesDao
import com.example.libraryapproom.bd.dao.LibrosDao
import com.example.libraryapproom.bd.dao.MainBaseDatos
import com.example.libraryapproom.bd.dao.PrestamosDao
import com.example.libraryapproom.bd.entidades.EstudiantesEntity
import com.example.libraryapproom.bd.entidades.LibrosModels
import com.example.libraryapproom.bd.entidades.PrestamosEntity
import com.example.libraryapproom.bd.viewmodel.PrestamoViewModel
import com.example.libraryapproom.databinding.FragmentAddPrestamoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.cacheGet
import org.json.JSONObject


class AddPrestamoFragment : Fragment() {
    lateinit var mService: ApiService
    lateinit var aBinding: FragmentAddPrestamoBinding
    private lateinit var viewModel: PrestamoViewModel
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aBinding = FragmentAddPrestamoBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(PrestamoViewModel::class.java)

        checkInternet()

        aBinding.btnGuardarP.setOnClickListener {
            guardarRegistroOffline()
        }

        mService = Common.retrofitService

        initSpinner(requireContext())
        count = 0
        return aBinding.root
    }

    //Cambiando Titulos de action bar
    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).supportActionBar?.title = "Agregar Prestamos"
    }

    private fun initSpinner(context: Context){
        val db: MainBaseDatos = MainBaseDatos.getDataBase(context)
        val daoEst: EstudiantesDao = db.estudiantesDao()
        val daoLib: LibrosDao = db.librosDao()

        //Arraylist para los spinners
        var arrayEst: ArrayList<String> = arrayListOf("Estudiantes...")
        var arrayLib: ArrayList<String> = arrayListOf("Libros...")

        CoroutineScope(Dispatchers.Main).launch {
            var listaEst: List<EstudiantesEntity> = daoEst.getAllStudents()
            var listaLibro: List<LibrosModels> = daoLib.getAll()

            //Lenan spinners

            listaEst.forEach{
                arrayEst.add(it.name)
            }

            listaLibro.forEach{
                arrayLib.add(it.nombreLibro)
            }
        }

        val  adapterEst: ArrayAdapter<String> = ArrayAdapter<String>(context, R.layout.spinner_item, arrayEst)
        aBinding.spEstudiante.adapter = adapterEst

        val adapterLib: ArrayAdapter<String> = ArrayAdapter<String>(context, R.layout.spinner_item, arrayLib)
        aBinding.spLibro.adapter = adapterLib
    }

    private fun guardarRegistro(){

        var studentID = aBinding.spEstudiante.selectedItemPosition.toString().toInt()
        var bookID = aBinding.spLibro.selectedItemPosition.toString().toInt()
        var fechaR = aBinding.TxtFechaRetiro.text.toString()
        var fechaE = aBinding.TxtFechaEntrega.text.toString()
        var id: Int = 0

        val jsonObject = JSONObject()
        jsonObject.put("borrowId", id)
        jsonObject.put("studentId", studentID)
        jsonObject.put("bookId", bookID)
        jsonObject.put("takenDate", fechaR)
        jsonObject.put("broughtDate", fechaE)

        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        val db: MainBaseDatos = MainBaseDatos.getDataBase(requireContext().applicationContext)
        val daoBorrow: PrestamosDao = db.prestamosDao()

        CoroutineScope(Dispatchers.IO).launch {
            if (count == 0) {
                mService.addBorrow(requestBody)

            }
        }

        Toast.makeText(
            requireContext(), "Registro guardado",
            Toast.LENGTH_LONG
        ).show()


        findNavController().navigate(R.id.listaPrestamo)

    }

    private fun checkInternet(){

        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner){isConnected ->
            if (isConnected){

                val db: MainBaseDatos = MainBaseDatos.getDataBase(requireContext().applicationContext)
                val daoB: PrestamosDao = db.prestamosDao()

                CoroutineScope(Dispatchers.IO).launch {
                    var prestamos: List<PrestamosEntity> = daoB.getAllPrestamos()
                    var prestamosRet = mService.getAllBorrows()
                    var prestamosSize = prestamos.size
                    var prestamosRetSize = prestamosRet.size

                    if ( prestamosSize > prestamosRetSize ){
                        for (i in 0..prestamos.lastIndex){
                            var id: Int = prestamos[i].id
                            var idEst: Int = (prestamos[i].student_id?.toInt() ?: Int) as Int
                            var idLib: Int = (prestamos[i].book_id?.toInt() ?: Int) as Int
                            var fechaR: String = prestamos[i].taken_date
                            var fechaE: String = prestamos[i].brought_date

                            val jsonObject = JSONObject()
                            jsonObject.put("borrowId", id)
                            jsonObject.put("studentId", idEst)
                            jsonObject.put("bookId", idLib)
                            jsonObject.put("takenDate", fechaR)
                            jsonObject.put("broughtDate", fechaE)

                            val jsonObjectString = jsonObject.toString()
                            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
                            println("Hola!, antes de enviar")

                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.eliminarPrestamo(prestamos[i].id)

                                mService.addBorrow(requestBody)
                            }
                        }
                    }
                }
            }
            else{
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
            if (!isConnected){
                val libro  = aBinding.spLibro.selectedItemPosition.toString().toInt()
                val est = aBinding.spEstudiante.selectedItemPosition.toString().toInt()
                val fechaR = aBinding.TxtFechaRetiro.text.toString()
                val fechaE = aBinding.TxtFechaEntrega.text.toString()
                val id: Int = 0


                val db: MainBaseDatos = MainBaseDatos.getDataBase(requireContext().applicationContext)
                val daoB: PrestamosDao = db.prestamosDao()
                val pres = PrestamosEntity(0, est, libro, fechaR, fechaE)

                CoroutineScope(Dispatchers.IO).launch {
                    println("Holi :3")

                    val idLibro = daoB.getByStringLibro(libro)
                    val idEst = daoB.getByStringEstudiantes(est)

                    viewModel.agregarPrestamo(pres)
                }
                Toast.makeText(requireContext(), "Registro guardado",
                    Toast.LENGTH_LONG).show()

                findNavController().navigate(R.id.listaPrestamo)

                Toast.makeText(
                    requireContext(),
                    "No internet connection",
                    Toast.LENGTH_LONG
                ).show()

            }
                else{
                    guardarRegistro()
                    count = 4
                }
        }

    }

}