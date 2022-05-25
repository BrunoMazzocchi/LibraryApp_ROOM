package com.example.libraryapproom.fragments.actualizar

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.libraryapproom.R
import com.example.libraryapproom.bd.entidades.PrestamosEntity
import com.example.libraryapproom.bd.viewmodel.PrestamoViewModel
import com.example.libraryapproom.databinding.FragmentActualizarPrestamoBinding

class ActualizarPrestamoFragment : Fragment() {
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

        with(uBinding){
            TxtLibro.setText(args.currentPrestamo.book_name)
            TxtEstudiante.setText(args.currentPrestamo.student_name)
            TxtFechaRetiro.setText(args.currentPrestamo.taken_date)
            TxtFechaEntrega.setText(args.currentPrestamo.brought_date)

            BtnEditarP.setOnClickListener {
                //guardarCambios()
            }
        }
        setHasOptionsMenu(true)
        return uBinding.root
    }

    /*private fun guardarCambios() {
        val libro = uBinding.TxtLibro.text.toString()
        val est = uBinding.TxtEstudiante.text.toString()
        val fechaR = uBinding.TxtFechaRetiro.text.toString()
        val fechaE = uBinding.TxtFechaEntrega.text.toString()

        val pres =
            PrestamosEntity(args.currentPrestamo.id, est, libro, fechaR, fechaE)

        viewModel.actualizarPrestamo(pres)
        Toast.makeText(requireContext(), "Registro actualizado",
            Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.listaPrestamo)
    }*/

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

    private fun eliminarPrestamo() {
        val alerta = AlertDialog.Builder(requireContext())
        alerta.setPositiveButton("Si") { _, _ ->
            viewModel.eliminarPrestamo(args.currentPrestamo.id)
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
                "Operación cancelada...",
                Toast.LENGTH_LONG
            ).show()
        }
        alerta.setTitle("Eliminando ${args.currentPrestamo.id}")
        alerta.setMessage("¿Esta seguro de eliminar a ${args.currentPrestamo.id}?")
        alerta.create().show()
    }
}