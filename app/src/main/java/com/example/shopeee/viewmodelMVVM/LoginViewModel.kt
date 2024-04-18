package com.example.shopeee.viewmodelMVVM

import androidx.lifecycle.ViewModel
import com.example.shopeee.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.mongodb.App
import io.realm.mongodb.Credentials
import io.realm.mongodb.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val realmApp: App
): ViewModel() {

    private val loginStateFlow = MutableStateFlow<Resource<User>>(Resource.Loading())
    fun observeLoginState(): Flow<Resource<User>> {
        return loginStateFlow
    }
    fun login(userName: String, password: String) {
        loginStateFlow.value = Resource.Loading()

        realmApp.loginAsync(Credentials.emailPassword(userName, password)) { result ->
            if (result.isSuccess) {
                val user = result.get()
                loginStateFlow.value = Resource.Success(user)

            } else {
                val error = result.error
                loginStateFlow.value = Resource.Error("Error logging in: ${error.localizedMessage}")
            }
        }
    }
}