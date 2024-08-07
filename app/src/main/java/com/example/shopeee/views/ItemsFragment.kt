package com.example.shopeee.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shopeee.api.ItemsApi
import com.example.shopeee.viewmodelMVVM.ItemsViewModel
import com.example.shopeee.R
import com.example.shopeee.repository.ItemsRepository
import com.example.shopeee.viewmodelfactoryMVVM.ItemsViewModelFactory

class ItemsFragment : Fragment() {

    private lateinit var factory: ItemsViewModelFactory
    private lateinit var viewModel: ItemsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.items_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val api = ItemsApi()
        val repository = ItemsRepository(api)
        factory = ItemsViewModelFactory(repository)
        viewModel = ViewModelProvider(this,factory)[ItemsViewModel::class.java]
    }

}