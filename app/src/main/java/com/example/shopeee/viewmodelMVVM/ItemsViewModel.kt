package com.example.shopeee.viewmodelMVVM

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopeee.repository.Item

class ItemsViewModel : ViewModel() {
    // add private val repository : ItemRepository

    private val itemsVM = MutableLiveData<List<Item>>()


}