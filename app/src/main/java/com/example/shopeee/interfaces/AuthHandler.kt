package com.example.shopeee.interfaces
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.shopeee.R
import com.example.shopeee.data.LoginResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthHandler(private val context: Context, private val retrofitInterface: RetrofitInterface?) {

    fun handleLoginDialog() {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.login_dialog, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view).show()
        val loginBtn = view.findViewById<Button>(R.id.login)
        val emailEdit = view.findViewById<EditText>(R.id.emailEdit)
        val passwordEdit = view.findViewById<EditText>(R.id.passwordEdit)

        loginBtn.setOnClickListener {
            val map = HashMap<String, String>()
            map["email"] = emailEdit.text.toString()
            map["password"] = passwordEdit.text.toString()

            val call = retrofitInterface!!.executeLogin(map)
            call.enqueue(object : Callback<LoginResult?> {
                override fun onResponse(call: Call<LoginResult?>, response: Response<LoginResult?>) {
                    if (response.code() == 200) {
                        val result = response.body()
                        val builder1 = AlertDialog.Builder(context)
                        builder1.setTitle(result!!.name)
                        builder1.setMessage(result.email)
                        builder1.show()
                    } else if (response.code() == 404) {
                        Toast.makeText(context, "Wrong username or password", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<LoginResult?>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    fun handleSignupDialog() {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.signup_dialog, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view).show()
        val signupBtn = view.findViewById<Button>(R.id.signup)
        val nameEdit = view.findViewById<EditText>(R.id.nameEdit)
        val emailEdit = view.findViewById<EditText>(R.id.emailEdit)
        val passwordEdit = view.findViewById<EditText>(R.id.passwordEdit)
        signupBtn.setOnClickListener {
            val map = HashMap<String, String>()
            map["name"] = nameEdit.text.toString()
            map["email"] = emailEdit.text.toString()
            map["password"] = passwordEdit.text.toString()
            val call = retrofitInterface!!.executeSignup(map)
            call.enqueue(object : Callback<Void?> {
                override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                    if (response.code() == 200) {
                        Toast.makeText(context, "Signed up successfully", Toast.LENGTH_LONG).show()
                    } else if (response.code() == 400) {
                        Toast.makeText(context, "Already registered", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Void?>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
