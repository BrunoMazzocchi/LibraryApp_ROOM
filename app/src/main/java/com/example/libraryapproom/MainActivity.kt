package com.example.libraryapproom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.libraryapproom.databinding.ActivityMainBinding
import com.example.libraryapproom.fragments.lista.FragmentLibro
import com.example.libraryapproom.fragments.lista.FragmentPrestamo

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(
            FragmentLibro()
        )

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.book    -> {
                    replaceFragment(
                        FragmentLibro()
                    )
                }
                R.id.borrow-> {
                    replaceFragment(
                        FragmentPrestamo()
                    )                }

            }
            return@setOnItemSelectedListener true
        }


    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }


}