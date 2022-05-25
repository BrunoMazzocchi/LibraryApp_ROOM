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
import com.example.libraryapproom.api.dataClass.Borrow
import com.example.libraryapproom.api.dataClass.Student
import com.example.libraryapproom.api.network.Common
import com.example.libraryapproom.api.network.NetworkConnection
import com.example.libraryapproom.bd.entidades.EstudiantesEntity
import com.example.libraryapproom.bd.entidades.PrestamosEntity
import com.example.libraryapproom.bd.viewmodel.EstudiantesViewModel
import com.example.libraryapproom.bd.viewmodel.LibrosViewModel
import com.example.libraryapproom.bd.viewmodel.PrestamoViewModel
import com.example.libraryapproom.databinding.FragmentPrestamoBinding
import com.example.libraryapproom.fragments.adapter.PrestamosAdapter
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FragmentPrestamo : Fragment() {
    lateinit var vBinding: FragmentPrestamoBinding

    private lateinit var viewModel: PrestamoViewModel

    private lateinit var viewModelStudent: EstudiantesViewModel

    private lateinit var mService: ApiService

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vBinding = FragmentPrestamoBinding.inflate(inflater,container,false)

        val adapter = PrestamosAdapter()
        val recycleView = vBinding.RcvPrestamos

        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(this).get(PrestamoViewModel::class.java)

        viewModelStudent = ViewModelProvider(this).get(EstudiantesViewModel::class.java)

        viewModel.lista.observe(viewLifecycleOwner, Observer { pres -> adapter.setData(pres) })

        setHasOptionsMenu(true)

        mService = Common.retrofitService

        return vBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        checkInternet()
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun checkInternet(){

        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                searchAllBorrows()
                searchAllStudents()
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


    private fun setupViews() {
        with(vBinding){
            BtnAgregar.setOnClickListener {
                it.findNavController().navigate(R.id.agregarPrestamo)
            }
        }
    }


    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuEliminar){
            eliminarTodo()
        }
        return super.onOptionsItemSelected(item)
    }*/


    private fun searchAllBorrows() {
        var list: ArrayList<Borrow>

        CoroutineScope(Dispatchers.IO).launch {
            var count:  Int = 0
            val call = mService.getAllBorrows()

            list = call

            list.forEach{_ ->
                run {
                    for (i in 0..list.lastIndex){
                       // var estudiante: String = list[i].student?.name.toString() + " " + list[i].student?.surname.toString()
                       // var libro: String = list[i].book?.name.toString()
                        var id: Int = (list[i].borrowId?.toInt() ?: Int) as Int
                        var studentID = (list[i].studentId)
                        var bookID = (list[i].bookId)
                        var fechaRetiro: String = list[i].takenDate.toString()
                        var fechaEntrega: String = list[i].broughtDate.toString()

                        var studentId: Int = (list[i].student?.studentId?.toInt() ?: Int) as Int
                        var name: String = list[i].student?.name.toString()
                        var surname: String = list[i].student?.surname.toString()
                        var dateOfBirth: String = list[i].student?.dateOfBirth.toString()
                        var gender: String = list[i].student?.gender.toString()
                        var classroom: String = list[i].student?.classroom.toString()
                        var point = list[i].student?.point

                        val estudiante =
                            EstudiantesEntity(studentId,name,surname,dateOfBirth,gender,classroom,point)


                        val prestamo =
                            PrestamosEntity(id, studentID, bookID, fechaRetiro, fechaEntrega)
                        count++

                        viewModel.agregarPrestamo(prestamo)
                        viewModelStudent.agregarEstudiante(estudiante)
                    }
                }
                if (count == list.size){
                    return@launch
                }
            }
        }
    }

    private fun searchAllStudents() {
        var list: ArrayList<Borrow>

        CoroutineScope(Dispatchers.IO).launch {
            var count:  Int = 0
            val call = mService.getAllBorrows()

            list = call

            list.forEach{_ ->
                run {
                    for (i in 0..list.lastIndex){
                        var studentId: Int = (list[i].student?.studentId?.toInt() ?: Int) as Int
                        var name: String = list[i].student?.name.toString()
                        var surname: String = list[i].student?.surname.toString()
                        var dateOfBirth: String = list[i].student?.dateOfBirth.toString()
                        var gender: String = list[i].student?.gender.toString()
                        var classroom: String = list[i].student?.classroom.toString()
                        var point = list[i].student?.point

                        val estudiante =
                            EstudiantesEntity(studentId,name,surname,dateOfBirth,gender,classroom,point)
                        count++

                        viewModelStudent.agregarEstudiante(estudiante)
                    }
                }
                if (count == list.size){
                    return@launch
                }
            }
        }
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