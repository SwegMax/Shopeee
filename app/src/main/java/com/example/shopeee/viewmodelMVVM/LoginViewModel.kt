package com.example.shopeee.viewmodelMVVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopeee.repository.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    private val _login = MutableSharedFlow<Resource<FirebaseUser>>()
    val login = _login.asSharedFlow()

    init {
        // Check if the user is already logged in when the ViewModel is created
        viewModelScope.launch {
            val currentUser = firebaseAuth.currentUser
            if (currentUser != null) {
                _login.emit(Resource.Success(currentUser))
            } else {
                _login.emit(Resource.Error("No user logged in"))
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _login.emit(Resource.Loading())
        }
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                viewModelScope.launch {
                    it.user?.let {
                        _login.emit(Resource.Success(it))
                    }
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _login.emit(Resource.Error(it.toString()))
                }
            }
    }


}
