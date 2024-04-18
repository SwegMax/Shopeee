package com.example.shopeee.viewmodelMVVM

import androidx.lifecycle.ViewModel
import com.example.shopeee.repository.Resource
import io.realm.mongodb.App
import io.realm.mongodb.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
        private val realmApp: App
): ViewModel() {

    private val registerStateFlow = MutableStateFlow<Resource<User>>(Resource.Loading())
    fun observeRegisterState(): Flow<Resource<User>> {
        return registerStateFlow
    }

    fun register(userName: String, password: String) {
        registerStateFlow.value = Resource.Loading()

        realmApp.emailPassword.registerUserAsync(userName, password) { result ->
            if (result.isSuccess) {
                /*val user = User
                registerStateFlow.value = Resource.Success(user)*/
                //broken, need to add more stuff
            } else {
                val error = result.error
                registerStateFlow.value = Resource.Error("Error registering: ${error.localizedMessage}")
            }
        }
    }
}