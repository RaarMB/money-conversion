package com.moneyconversion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import com.moneyconversion.databinding.ActivityMainBinding
import com.moneyconversion.splash.SplashConversionFragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.moneyconversion.splash.SplashConversionFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationToolbarDelegate, SplashConversionFragment.SplashConversionListener {

    private var _binding: ActivityMainBinding? = null

    private val binding get() = _binding!!

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return false
    }

    override fun goToHome() {
        navController.navigate(SplashConversionFragmentDirections.actionToHomeConversion())
    }

    override fun showToolbar() {
        supportActionBar?.show()
    }

    override fun hideToolbar() {
        supportActionBar?.hide()
    }
}