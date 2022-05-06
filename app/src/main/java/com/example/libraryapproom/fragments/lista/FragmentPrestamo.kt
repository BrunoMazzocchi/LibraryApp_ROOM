package com.example.libraryapproom.fragments.lista

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryapproom.databinding.FragmentPrestamoBinding
import com.example.libraryapproom.fragments.adapter.LibrosAdapter

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

    }



}