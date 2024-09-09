package com.example.shopeee.views.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.shopeee.R
import com.example.shopeee.repository.Category
import com.example.shopeee.repository.Resource
import com.example.shopeee.viewmodelMVVM.CategoryViewModel
import com.example.shopeee.viewmodelMVVM.factory.BaseCategoryViewModelFactoryFactory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DrinksFragment: BaseCategoryFragment() {
    @Inject
    lateinit var firestore: FirebaseFirestore

    val viewModel by viewModels<CategoryViewModel> {
        BaseCategoryViewModelFactoryFactory(firestore, Category.Drinks)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.offerProducts.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            showOfferLoading()
                        }

                        is Resource.Success -> {
                            offerAdapter.differ.submitList(it.data)
                            hideOfferLoading()
                        }

                        is Resource.Error -> {
                            Snackbar.make(
                                requireView(),
                                "snackOfferProd error",
                                Snackbar.LENGTH_LONG
                            )
                                .show()
                            hideOfferLoading()
                        }

                        else -> Unit
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.bestProducts.collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            showBestProductsLoading()
                        }

                        is Resource.Success -> {
                            bestProductsAdapter.differ.submitList(it.data)
                            hideBestProductsLoading()
                        }

                        is Resource.Error -> {
                            Snackbar.make(
                                requireView(),
                                "it.message.toString()",
                                Snackbar.LENGTH_LONG
                            )
                                .show()
                            hideBestProductsLoading()
                        }

                        else -> Unit
                    }
                }
            }
        }
    }

    override fun onBestProductsPagingRequest() {

    }

    override fun onOfferPagingRequest() {

    }
}
