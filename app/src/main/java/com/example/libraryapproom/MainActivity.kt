package com.example.libraryapproom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.libraryapproom.bd.viewmodel.LibrosViewModel
import com.example.libraryapproom.bd.viewmodel.PrestamoViewModel
import com.example.libraryapproom.bd.viewmodel.viewmodelFactory.LibrosViewModelFactory
import com.example.libraryapproom.bd.viewmodel.viewmodelFactory.PrestamoViewModelFactory
import com.example.libraryapproom.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    lateinit var viewModel: LibrosViewModel
    lateinit var viewModelP: PrestamoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        NavHost()
        setUpViewModelLibro()
        setUpViewModelPrestamo()




    }

    private fun NavHost(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.findNavController()

        //BottomNavegation
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun setUpViewModelLibro(){
        val viewModelProviderFactory = LibrosViewModelFactory(application)

        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(LibrosViewModel::class.java)
    }

    private fun setUpViewModelPrestamo(){
        val viewModelProviderFactoryPrestamo = PrestamoViewModelFactory(application)

        viewModelP =
            ViewModelProvider(this, viewModelProviderFactoryPrestamo).get(PrestamoViewModel::class.java)
    }

}