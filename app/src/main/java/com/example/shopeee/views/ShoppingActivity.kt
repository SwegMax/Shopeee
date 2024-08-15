package com.example.shopeee.views

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.shopeee.R
import com.example.shopeee.databinding.ActivityShoppingBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ShoppingActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityShoppingBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*val navController = findNavController(R.id.shoppingHostFragment)
        binding.bottomNavigation.setupWithNavController(navController)*/

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val navController = Navigation.findNavController(this, R.id.shoppingHostFragment)
        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }
}
