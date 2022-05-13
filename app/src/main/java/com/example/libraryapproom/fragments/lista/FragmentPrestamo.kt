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
        viewModel.lista.observe(viewLifecycleOwner, Observer { pres -> adapter.setData(pres) })

        setHasOptionsMenu(true)

        return vBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        with(vBinding){
            BtnAgregar.setOnClickListener {
                it.findNavController().navigate(R.id.agregarPrestamo)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuEliminar){
            eliminarTodo()
        }
        return super.onOptionsItemSelected(item)
    }

    fun getRetrofit():Retrofit{
        return Retrofit
            .Builder()
            .baseUrl("http://localhost:9091/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //fun mostrarborrows(){
    //CoroutineScope(Dispatchers.Main).launch {
    // try {
    //  val call = getRetrofit().create(ApiService::class.java).getBorrow("borrows/all")
    //   if (call.isSuccessful){
    //        val serviceTag:String = call.body()?.data?.serviceTag.toString()
    //       val emblem: String = call.body()?.data?.emblemUrl.toString()
    //    val backdrop: String = call.body()?.data?.backdropImageUrl.toString()
    //    val gamer: String = call.body()?.additional?.parameters?.gamertag.toString()
    //      binding.txtService.text = "Service tag: $serviceTag"
    //      binding.txGamertag.text = "$gamer"
    //       Picasso.get().load(emblem).into(binding.imgEmblem)
    //      Picasso.get().load(backdrop).into(binding.ivBackDrop)
    //   }
    // }catch (ex: Exception){

    // }
    //}
    //}

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