package com.example.shopeee.api

import retrofit2.Response
import java.io.IOException

abstract class SafeApiRequest {

    suspend fun <T: Any> apiRequest(call: suspend () -> Response<T>) : T{
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            //handle api exception here
            throw ApiException(response.code().toString())
        }
    }
}

class ApiException(message: String): IOException(message)