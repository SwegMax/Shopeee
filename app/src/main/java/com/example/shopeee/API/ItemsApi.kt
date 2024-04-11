package com.example.shopeee.API

import com.example.shopeee.repository.Item
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ItemsApi {

    @GET("items")
    suspend fun getItems() : Response<List<Item>>
    //suspend will call a coroutine context to handle this API call w/o blocking main thread

    companion object{
        operator fun invoke() : ItemsApi{
            return Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://app.something")
                    .build()
                    .create(ItemsApi::class.java)
        }
    }
}