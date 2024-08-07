package com.example.shopeee.repository

import com.example.shopeee.api.ItemsApi
import com.example.shopeee.api.SafeApiRequest

class ItemsRepository(private val api: ItemsApi) : SafeApiRequest() {
    suspend fun getItems() = apiRequest { api.getItems() }
}