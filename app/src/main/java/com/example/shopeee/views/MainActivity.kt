package com.example.shopeee.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopeee.R
import com.example.shopeee.adapter.RecyclerViewAdapter
import com.example.shopeee.repository.Item
import com.example.shopeee.controllerMVC.AuthController
import com.example.shopeee.databinding.MainActivityBinding
import io.realm.Realm
import io.realm.mongodb.App

//This will be the Market activity!

class MainActivity : AppCompatActivity() {
    private lateinit var newRecyclerView : RecyclerView
    private lateinit var newArrayList : ArrayList<Item>
    lateinit var itemImage : Array<Int>
    lateinit var heading : Array<String>
    lateinit var id : Array<Long>
    lateinit var quantity : Array<Int>

    var mainBinding : MainActivityBinding? = null
    lateinit var app : App
    private val appId = "shopeee-zuqyq"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(R.layout.first_activity)
        replaceFragment(Profile())

        //Realm
        Realm.init(this)
        app = App(appId)

        val authHandler = AuthController(this, app)
        //Items list
        //replace with database array
        itemImage = arrayOf()
        heading = arrayOf()

        newRecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = GridLayoutManager(this, 2)
        newRecyclerView.layoutManager = layoutManager
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<Item>()
        getUserData()

        mainBinding!!.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> replaceFragment(Profile())
                R.id.market -> replaceFragment(Market())
                R.id.logout -> logoutUser()

                else -> {

                }
            }
            true
        }


    }
    private fun getUserData() {
        for(i in itemImage.indices) {
            val item = Item(itemImage[i],heading[i],id[i],quantity[i])
            newArrayList.add(item)
        }
        newRecyclerView.adapter = RecyclerViewAdapter(newArrayList)
    }

    //attach a view to each fragment
    //Create bottom bar nav: https://www.youtube.com/watch?v=L_6poZGNXOo

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit()
    }

    private fun logoutUser() {
        // Implement your logout logic here, such as clearing user session, navigating to login screen, etc.
        // For example:
        startActivity(Intent(this, FirstActivity::class.java))
        finish() // Close the current activity after logging out
    }

}