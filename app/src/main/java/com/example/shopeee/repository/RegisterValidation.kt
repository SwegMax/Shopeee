package com.example.shopeee.repository

sealed class RegisterValidation {
    data object Success: RegisterValidation()
    data class Failed(val message: String): RegisterValidation()
}

data class RegisterFieldState(
        val username: RegisterValidation,
        val password: RegisterValidation
)