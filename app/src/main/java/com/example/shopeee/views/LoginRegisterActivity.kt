package com.example.shopeee.views

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.shopeee.R
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            startActivity(Intent(this, ShoppingActivity::class.java))
            finish()
        } else {
            setContentView(R.layout.activity_login_register)
        }
        Log.d(TAG, "LoginRegisterActivity loaded")
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}
