package com.example.shopeee.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopeee.R
import com.example.shopeee.data.Items
import com.example.shopeee.interfaces.AuthHandler
import com.example.shopeee.interfaces.RetrofitInterface
import io.realm.Realm
import io.realm.mongodb.App
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var retrofit: Retrofit? = null //change to val?
    private var retrofitInterface: RetrofitInterface? = null
    private val BASE_URL = "http://10.0.2.2:3000" //only for mac localhost

    private lateinit var newRecyclerView : RecyclerView
    private lateinit var newArrayList : ArrayList<Items>
    lateinit var imageId : Array<Int>
    lateinit var heading : Array<String>

    lateinit var app : App
    private val appId = "shopeee-zuqyq"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Realm
        Realm.init(this)
        app = App(appId)

        if (retrofit != null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        retrofitInterface = retrofit?.create(RetrofitInterface::class.java)
        val authHandler = AuthHandler(this, retrofitInterface)
        authHandler.handleLoginDialog()
        findViewById<View>(R.id.login).setOnClickListener{
            AuthHandler(this, retrofitInterface).handleLoginDialog() }
        findViewById<View>(R.id.signup).setOnClickListener {
            AuthHandler(this, retrofitInterface).handleSignupDialog() }

        //replace with database array
        imageId = arrayOf()
        heading = arrayOf()

        newRecyclerView = findViewById(R.id.recyclerView)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true) //might not scale for diff screen size

        newArrayList = arrayListOf<Items>()
        getUserData()


    }

    /* Shopee uses a lot of override, @Override or override method type
    Generated by Dagger - CoroutineModule_...DispatcherFactory
    -init Lazy
    -where does the user data go?
    -add secure login
    -Exceptions/error message when server is not available

    Change Login and Register to Fragments to reduce memory usage
    Add side bar
    Add login activity
    Add logout button
    Add SLS login or sth
    Make Handler class for login and signup dialogs

    jervis
    qPfg9k1ydEL7VnVt
    */

    private fun getUserData() {
        for(i in imageId.indices) {
            val item = Items(imageId[i],heading[i])
            newArrayList.add(item)
        }
        newRecyclerView.adapter = RecyclerViewAdapter(newArrayList)
    }
}