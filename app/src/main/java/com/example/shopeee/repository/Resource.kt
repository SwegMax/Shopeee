package com.example.shopeee.repository

sealed class Resource<T>(val data: T? = null, message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class SuccessWithString(message: String) : Resource<Nothing>(message = message)
    class Error<T>(message: String) : Resource<T>(message = message)
    class Loading<T> : Resource<T>()
    class Unspecified : Resource<List<Product>>() {

    }
}

