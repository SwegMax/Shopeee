package com.example.shopeee.views

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.shopeee.R
import com.example.shopeee.repository.Resource
import com.example.shopeee.viewmodelMVVM.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginRegisterActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)
        observeLoginState()
        Log.d(TAG, "LoginRegisterActivity loaded")
    }
    private fun observeLoginState() {
        lifecycleScope.launch {
            viewModel.login.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        if (resource.data == null) {
                            // User is already logged in, navigate to ShoppingActivity
                            val intent = Intent(this@LoginRegisterActivity, ShoppingActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        } else {
                        }
                    }
                    is Resource.Error -> {
                    }
                    else -> {
                    }
                }
            }
        }
    }
}
