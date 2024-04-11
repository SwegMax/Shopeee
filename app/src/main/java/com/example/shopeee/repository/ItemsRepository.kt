package com.example.shopeee.repository

import com.example.shopeee.API.ItemsApi
import com.example.shopeee.API.SafeApiRequest

class ItemsRepository(private val api: ItemsApi) : SafeApiRequest() {
    suspend fun getItems() = apiRequest { api.getItems() }
}