package com.example.shopeee.handlers

import android.util.Patterns
import com.example.shopeee.repository.RegisterValidation

fun validateEmail(email: String): RegisterValidation{
    if (email.isEmpty())
        return RegisterValidation.Failed("Email cannot be empty")

    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return RegisterValidation.Failed("Wrong email format")

    return RegisterValidation.Success
}

fun validatePassword(password: String): RegisterValidation{
    if(password.isEmpty())
        return RegisterValidation.Failed("Password cannot be empty")

    if(password.length < 6)
        return RegisterValidation.Failed("Password should contain at least 6 char")

    return RegisterValidation.Success
}