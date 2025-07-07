package com.example.shopeee.viewmodelMVVM

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopeee.handlers.validateEmail
import com.example.shopeee.handlers.validatePassword
import com.example.shopeee.repository.Constants.USER_COLLECTION
import com.example.shopeee.repository.RegisterFieldState
import com.example.shopeee.repository.RegisterValidation
import com.example.shopeee.repository.Resource
import com.example.shopeee.repository.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
        private val firebaseAuth: FirebaseAuth,
        private val db:FirebaseFirestore
): ViewModel() {

    private val _register = MutableStateFlow<Resource<User>>(Resource.Loading())
    val register: Flow<Resource<User>> = _register

    private val _validation = Channel<RegisterFieldState>()
    val validation = _validation.receiveAsFlow()

    fun createAccountWithEmailAndPassword(user: User, password: String) {
        if (checkValidation(user, password)) {
            viewModelScope.launch {
                _register.emit(Resource.Loading())
                try {
                    val authResult = firebaseAuth.createUserWithEmailAndPassword(user.email, password).await()
                    val firebaseUser = authResult.user

                    if (firebaseUser != null) {
                        saveUserInfo(firebaseUser.uid, user)
                    }

                    _register.emit(Resource.Success(user))

                } catch (e: Exception) {
                    val errorMsg = e.message ?: "Unknown error occurred during registration"
                    _register.emit(Resource.Error(errorMsg))
                }

                }
        } else {
            val registerFieldState = RegisterFieldState(
                validateEmail(user.email), validatePassword(password)
            )
            viewModelScope.launch {
                _validation.send(registerFieldState)
            }
        }
    }

    private fun saveUserInfo(userUid: String, user: User){
        db.collection(USER_COLLECTION)
                .document(userUid)
                .set(user)
                .addOnSuccessListener {
                    _register.value = Resource.Success(user)
                }
                .addOnFailureListener {
                    _register.value = Resource.Error(it.toString())
                }
    }

    private fun checkValidation(user: User, password: String): Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordValidation = validatePassword(password)

        return emailValidation is RegisterValidation.Success &&
                passwordValidation is RegisterValidation.Success

    }
}