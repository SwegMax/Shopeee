package com.example.shopeee.viewmodelMVVM

import androidx.lifecycle.ViewModel
import com.example.shopeee.handlers.validateEmail
import com.example.shopeee.handlers.validatePassword
import com.example.shopeee.repository.RegisterFieldState
import com.example.shopeee.repository.RegisterValidation
import com.example.shopeee.repository.Resource
import io.realm.mongodb.App
import io.realm.mongodb.User
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
        private val realmApp: App
): ViewModel() {

    private val registerStateFlow = MutableStateFlow<Resource<User>>(Resource.Loading())

    private val validationChannel = Channel<RegisterFieldState>()
    val validation = validationChannel.receiveAsFlow()

    fun observeRegisterState(): Flow<Resource<User>> {
        return registerStateFlow
    }

    fun register(userName: String, password: String) {
        if (checkValidation(userName, password)) {
            runBlocking { registerStateFlow.emit(Resource.Loading()) }

            realmApp.emailPassword.registerUserAsync(userName, password) { result ->
                if (result.isSuccess) {
                    /*val user = User()
                    registerStateFlow.value = Resource.Success(user)*/
                } else {
                    val error = result.error
                    registerStateFlow.value = Resource.Error("Error registering: ${error.localizedMessage}")
                }
            }
        } else {
            val registerFieldState = RegisterFieldState(
                validateEmail(userName), validatePassword(password)
            )
            runBlocking {
                validationChannel.send(registerFieldState)
            }
        }
    }

    private fun checkValidation(userName: String, password: String): Boolean {
        val emailValidation = validateEmail(userName)
        val passwordValidation = validatePassword(password)

        return emailValidation is RegisterValidation.Success &&
                passwordValidation is RegisterValidation.Success

    }
}