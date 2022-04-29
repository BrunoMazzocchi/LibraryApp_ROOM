package com.example.libraryapproom.fragments.lista

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapproom.LibrosProvider
import com.example.libraryapproom.databinding.FragmentLibroBinding
import com.example.libraryapproom.fragments.lista.adapter.LibrosAdapter


class FragmentLibro : Fragment() {
    lateinit var binding: FragmentLibroBinding
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
}
    fun initRecyclerView(){

        binding.RcvLibro.layoutManager = LinearLayoutManager(this.context)
        binding.RcvLibro.adapter = LibrosAdapter(LibrosProvider.BookList)

    }
}