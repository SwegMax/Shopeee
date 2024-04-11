package com.example.shopeee.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shopeee.viewmodelMVVM.ItemsViewModel
import com.example.shopeee.R

class ItemsFragment : Fragment() {


    private lateinit var viewModel: ItemsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.items_fragment, container, false)
    }



}