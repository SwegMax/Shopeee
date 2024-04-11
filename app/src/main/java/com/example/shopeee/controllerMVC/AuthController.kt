package com.example.shopeee.controllerMVC
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.shopeee.databinding.LoginDialogBinding
import com.example.shopeee.databinding.SignupDialogBinding
import io.realm.mongodb.App
import io.realm.mongodb.Credentials

class AuthController(private val context: Context, private val app : App) {

    var loginBinding : LoginDialogBinding? = null
    var signUpBinding : SignupDialogBinding? = null

    fun handleLoginDialog() {
        val inflater = LayoutInflater.from(context)
        //val view = inflater.inflate(R.layout.login_dialog, null)
        loginBinding = LoginDialogBinding.inflate(inflater)

        val builder = AlertDialog.Builder(context)
        builder.setView(loginBinding!!.root).show()
        /*val loginBtn = loginBinding!!.loginBtn
        val emailEdit = loginBinding!!.emailEdit
        val passwordEdit = loginBinding!!.passwordEdit

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
        }*/

        val email = loginBinding!!.emailEdit.text.toString()
        val password = loginBinding!!.passwordEdit.text.toString()

        val credentials = Credentials.emailPassword(email,password)
        app.loginAsync(credentials){result ->
            if (result.error == null) {
                Toast.makeText(context, "Logged in as ${result.get().id}",
                        Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "Error logging in ${result.error.localizedMessage}",
                        Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun handleSignupDialog() {
        /*val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.signup_dialog, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view).show()
        val signupBtn = view.findViewById<Button>(R.id.signupBtn)
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
        }*/

        val inflater = LayoutInflater.from(context)
        //val view = inflater.inflate(R.layout.login_dialog, null)
        signUpBinding = SignupDialogBinding.inflate(inflater)

        val builder = AlertDialog.Builder(context)
        builder.setView(signUpBinding!!.root).show()

        val email = signUpBinding!!.emailEdit.text.toString()
        val password = signUpBinding!!.passwordEdit.text.toString()

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
