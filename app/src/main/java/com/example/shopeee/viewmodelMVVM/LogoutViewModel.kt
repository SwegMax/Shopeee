package com.example.shopeee.viewmodelMVVM

import androidx.lifecycle.ViewModel
import com.example.shopeee.repository.Resource
import com.example.shopeee.repository.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
): ViewModel() {

    private val _user = MutableStateFlow<Resource<User>>(Resource.UnspecifiedUser())
    val user = _user.asSharedFlow()

    /*init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            _user.emit(Resource.Loading())
        }
        firestore.collection("user").document(auth.uid!!)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    viewModelScope.launch {
                        _user.emit(Resource.Error(error.message.toString()))
                    }
                } else {
                    val user = value?.toObject(User::class.java)
                    user?.let {
                        viewModelScope.launch {
                            _user.emit(Resource.Success(user))
                        }
                    }
                }
            }
    }*/

    fun logout() {
        auth.signOut()
    }
}