package com.example.shopeee.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shopeee.R
import com.example.shopeee.databinding.ActivityShoppingBinding

class ShoppingActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityShoppingBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        val navController = findNavController(R.id.host_fragment)
        binding.bottomNavigation.setupWithNavController(navController)
    }
}