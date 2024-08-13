package com.example.shopeee.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shopeee.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        Log.d(TAG, "LoginRegisterActivity loaded")
    }
}
