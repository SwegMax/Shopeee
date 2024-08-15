package com.example.shopeee.controllerMVC
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.shopeee.databinding.FragmentLoginBinding
import com.example.shopeee.databinding.FragmentRegisterBinding
import com.example.shopeee.interfaces.RetrofitInterface
import com.example.shopeee.repository.Item
import io.realm.mongodb.App
import io.realm.mongodb.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthController(private val context: Context, private val app : App) {
    private lateinit var binding: FragmentLoginBinding
    var loginBinding : FragmentLoginBinding? = null
    var signUpBinding : FragmentRegisterBinding? = null

    private val BASE_URL = "http://localhost:8080"

    fun handleLoginDialog() {
        val inflater = LayoutInflater.from(context)
        //val view = inflater.inflate(R.layout.login_dialog, null)
        loginBinding = FragmentLoginBinding.inflate(inflater)

        val builder = AlertDialog.Builder(context)
        builder.setView(loginBinding!!.root).show()



        val email = loginBinding!!.edEmailLogin.text.toString()
        val password = loginBinding!!.edPasswordLogin.text.toString()

        val credentials = Credentials.emailPassword(email,password)
        app.loginAsync(credentials){result ->
            if (result.error == null) {
                Toast.makeText(context, "Logged in as ${result.get().id}",
                        Toast.LENGTH_SHORT).show()
                //Get user session
                val userSession = result.get()
                fetchUserData(userSession.accessToken)
            }
            else {
                Toast.makeText(context, "Error logging in ${result.error.localizedMessage}",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun fetchUserData(accessToken: String) {
        val userID = "1231445151"

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        val service = retrofit.create(RetrofitInterface::class.java)

        val call: Call<List<Item>> = service.fetchItems("Bearer $accessToken", userID)
        call.enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if(response.isSuccessful) {
                    val items = response.body()
                } else {
                    Toast.makeText(context, "Failed to fetch items", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Item>>, t:Throwable) {
                Toast.makeText(context, "Failed to fetch items: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun handleSignupDialog() {
        val inflater = LayoutInflater.from(context)
        //val view = inflater.inflate(R.layout.login_dialog, null)
        signUpBinding = FragmentRegisterBinding.inflate(inflater)

        val builder = AlertDialog.Builder(context)
        builder.setView(signUpBinding!!.root).show()

        val email = signUpBinding!!.edEmailRegister.text.toString()
        val password = signUpBinding!!.edPasswordRegister.text.toString()

        app.emailPassword.registerUserAsync(email,password) {result ->
            if (result.error == null) {
                Toast.makeText(context, "User registered", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "Error signing up ${result.error.localizedMessage}",
                        Toast.LENGTH_SHORT).show()
            }

        }
    }
}
