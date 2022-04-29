package com.example.libraryapproom.fragments.lista

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapproom.LibrosProvider
import com.example.libraryapproom.R
import com.example.libraryapproom.databinding.FragmentLibroBinding
import com.example.libraryapproom.databinding.FragmentPrestamoBinding
import com.example.libraryapproom.fragments.lista.adapter.LibrosAdapter
import kotlinx.android.synthetic.main.fragment_prestamo.*

class FragmentPrestamo : Fragment() {
    lateinit var binding: FragmentPrestamoBinding
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrestamoBinding.inflate(inflater,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }
    fun initRecyclerView(){

        binding.RcvPrestamos.layoutManager = LinearLayoutManager(this.context)
        binding.RcvPrestamos.adapter = LibrosAdapter(LibrosProvider.BookList)

    }


}