package com.example.shopeee.viewmodelfactoryMVVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shopeee.repository.ItemsRepository
import com.example.shopeee.viewmodelMVVM.ItemsViewModel

@Suppress("UNCHECKED_CAST")
class ItemsViewModelFactory(private val repository: ItemsRepository)
    : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T{
            return ItemsViewModel(repository) as T
        }
}