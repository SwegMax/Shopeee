package com.example.shopeee.viewmodelMVVM

import androidx.lifecycle.ViewModel
import com.example.shopeee.handlers.validateEmail
import com.example.shopeee.handlers.validatePassword
import com.example.shopeee.repository.Constants.USER_COLLECTION
import com.example.shopeee.repository.RegisterFieldState
import com.example.shopeee.repository.RegisterValidation
import com.example.shopeee.repository.Resource
import com.example.shopeee.repository.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
        private val firebaseAuth: FirebaseAuth,
        private val db:FirebaseFirestore
): ViewModel() {

    private val registerStateFlow = MutableStateFlow<Resource<User>>(Resource.Loading())
    val register: Flow<Resource<User>> = registerStateFlow

    private val validationChannel = Channel<RegisterFieldState>()
    val validation = validationChannel.receiveAsFlow()

    fun register(user: User, password: String) {
        if (checkValidation(user, password)) {
            runBlocking { registerStateFlow.emit(Resource.Loading()) }
            firebaseAuth.createUserWithEmailAndPassword(user.email, password)
                    .addOnSuccessListener {
                        it.user?.let {
                            saveUserInfo(it.uid, user)
                        }
                    }
                    .addOnFailureListener {
                        registerStateFlow.value = Resource.Error(it.toString())
                        //supposed to have .message in the middle
                    }

        } else {
            val registerFieldState = RegisterFieldState(
                validateEmail(user.email), validatePassword(password)
            )
            runBlocking {
                validationChannel.send(registerFieldState)
            }
        }
    }

    private fun saveUserInfo(userUid: String, user: User){
        db.collection(USER_COLLECTION)
                .document(userUid)
                .set(user)
                .addOnSuccessListener {
                    registerStateFlow.value = Resource.Success(user)
                }
                .addOnFailureListener {
                    registerStateFlow.value = Resource.Error(it.toString())
                }
    }

    private fun checkValidation(user: User, password: String): Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordValidation = validatePassword(password)

        return emailValidation is RegisterValidation.Success &&
                passwordValidation is RegisterValidation.Success

    }
}