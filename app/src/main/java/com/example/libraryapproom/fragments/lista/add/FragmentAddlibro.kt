package com.example.libraryapproom.fragments.lista.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.libraryapproom.R
import com.example.libraryapproom.databinding.FragmentAddlibroBinding


class FragmentAddlibro: Fragment() {
    lateinit var fBinding: FragmentAddlibroBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fBinding= FragmentAddlibroBinding.inflate(layoutInflater)
        return fBinding.root
    }

}