package com.example.shopeee.views

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.shopeee.R
import com.example.shopeee.databinding.ActivityShoppingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityShoppingBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.shoppingHostFragment) as? NavHostFragment
        val navController = navHostFragment?.navController

        if (navController != null) {
            binding.bottomNavigation.setupWithNavController(navController)
        } else {
            Log.e("ShoppingActivity", "NavController not found")
        }
    }
}
