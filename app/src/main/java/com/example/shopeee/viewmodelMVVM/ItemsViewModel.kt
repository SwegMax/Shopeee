package com.example.shopeee.viewmodelMVVM

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopeee.repository.Item
import com.example.shopeee.repository.ItemsRepository

class ItemsViewModel(private val repository : ItemsRepository) : ViewModel() {

    private val itemsStream = MutableLiveData<List<Item>>()
    val itemsData: LiveData<List<Item>>
        get() = itemsStream

    suspend fun getItems() {
        val itemsData = repository.getItems()
        itemsStream.value = itemsData
    }
}