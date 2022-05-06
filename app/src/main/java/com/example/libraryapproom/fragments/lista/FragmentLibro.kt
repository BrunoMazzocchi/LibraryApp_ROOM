package com.example.libraryapproom.fragments.lista

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapproom.LibrosProvider
import com.example.libraryapproom.NavGraphDirections
import com.example.libraryapproom.databinding.FragmentLibroBinding
import com.example.libraryapproom.fragments.lista.adapter.LibrosAdapter


class FragmentLibro : Fragment() {
    lateinit var binding: FragmentLibroBinding
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLibroBinding.inflate(inflater,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        binding.BtnAgregar.setOnClickListener {
            val action = NavGraphDirections.actionGlobalFragmentAddlibro()
            findNavController().navigate(action)
        }
    }
    fun initRecyclerView(){

        binding.RcvLibro.layoutManager = LinearLayoutManager(this.context)
        binding.RcvLibro.adapter = LibrosAdapter(LibrosProvider.BookList)

    }

}