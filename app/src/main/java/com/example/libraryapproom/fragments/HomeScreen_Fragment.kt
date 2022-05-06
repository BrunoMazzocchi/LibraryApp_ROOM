package com.example.libraryapproom.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.libraryapproom.R
import com.example.libraryapproom.databinding.FragmentHomeScreenBinding

class HomeScreen_Fragment : Fragment() {
  lateinit var homebinding: FragmentHomeScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homebinding = FragmentHomeScreenBinding.inflate(layoutInflater)
        return homebinding.root
        startSplashScreen()
    }

    private fun startSplashScreen(timeDelayInMilis : Long = 2000) {
        Handler(Looper.myLooper()!!).postDelayed(
            {
            }, timeDelayInMilis
        )
    }


}