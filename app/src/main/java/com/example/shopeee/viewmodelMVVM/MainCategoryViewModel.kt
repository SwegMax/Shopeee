package com.example.shopeee.viewmodelMVVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopeee.repository.Product
import com.example.shopeee.repository.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainCategoryViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _bestDealsProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestDealsProducts : StateFlow<Resource<List<Product>>> = _bestDealsProducts

    private val _bestProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestProducts : StateFlow<Resource<List<Product>>> = _bestProducts

    private val pagingInfo = PagingInfo()

    init {
        fetchBestDeals()
        fetchBestProducts()
    }
    fun fetchBestDeals(){
        viewModelScope.launch {
            _bestDealsProducts.emit(Resource.Loading())
        }
        firestore.collection("Products")
            .whereEqualTo("category", "Best Deals").get()
            .addOnSuccessListener { result ->
                val bestDealsProducts = result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _bestDealsProducts.emit(Resource.Success(bestDealsProducts))
                }
            } .addOnFailureListener {
                viewModelScope.launch {
                    _bestDealsProducts.emit(Resource.Error(it.message.toString()))
                }
            }
    }

    fun fetchBestProducts(){
        if(!pagingInfo.isPagingEnd) {
            viewModelScope.launch {
                _bestProducts.emit(Resource.Loading())
                firestore.collection("Products").limit(pagingInfo.bestProductsPage * 10).get()
                    .addOnSuccessListener { result ->
                        val bestProducts = result.toObjects(Product::class.java)
                        pagingInfo.isPagingEnd = bestProducts == pagingInfo.oldBestProducts
                        pagingInfo.oldBestProducts = bestProducts
                        viewModelScope.launch {
                            _bestProducts.emit(Resource.Success(bestProducts))
                        }
                        pagingInfo.bestProductsPage++
                    }.addOnFailureListener {
                        viewModelScope.launch {
                            _bestProducts.emit(Resource.Error(it.message.toString()))
                        }
                    }
            }
        }
    }

}

internal data class PagingInfo(
    var bestProductsPage: Long = 1,
    var oldBestProducts: List<Product> = emptyList(),
    var isPagingEnd: Boolean = false
)