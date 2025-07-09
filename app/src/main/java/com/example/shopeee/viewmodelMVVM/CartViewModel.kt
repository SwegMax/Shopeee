package com.example.shopeee.viewmodelMVVM

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopeee.helper.getProductPrice
import com.example.shopeee.repository.CartProduct
import com.example.shopeee.repository.Resource
import com.example.shopeee.repository.firebase.FirebaseCommon
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val firebaseCommon: FirebaseCommon
): ViewModel() {

    private val _cartProducts =
        MutableStateFlow<Resource<List<CartProduct>>>(Resource.Unspecified())
    val cartProducts = _cartProducts.asStateFlow()

    val productsPrice = cartProducts.map {
        when(it) {
            is Resource.Success -> {
                calculatePrice(it.data!!)
            }
            else -> null
        }
    }

    private var cartProductDocuments = emptyList<DocumentSnapshot>()

    private val _deleteDialog = MutableSharedFlow<CartProduct>()
    val deleteDialog = _deleteDialog.asSharedFlow()



    fun deleteCartProduct(cartProduct: CartProduct) {
        val updatedSnapshot = cartProductDocuments.find { snapshot ->
            val snapshotProduct = snapshot.toObject(CartProduct::class.java)?.product
            snapshotProduct?.id == cartProduct.product.id
        }

        if (updatedSnapshot != null) {
            val documentId = updatedSnapshot.id
            viewModelScope.launch { _cartProducts.emit(Resource.Loading()) }
            firestore.collection("user").document(auth.uid!!).collection("cart")
                .document(documentId).delete()
        } else {
            viewModelScope.launch {
                _cartProducts.emit(Resource.Error("Could not delete product. It has already been removed."))
            }
        }
    }

    private fun calculatePrice(data: List<CartProduct>): Float {
        return data.sumOf { cartProduct -> //used to be sumByDouble
            (cartProduct.product.offerPercentage.getProductPrice(cartProduct.product.price) * cartProduct.quantity).toDouble()
        }.toFloat()
    }

    init {
        getCartProducts()
    }

    private fun getCartProducts() {
        viewModelScope.launch { _cartProducts.emit(Resource.Loading()) }
        firestore.collection("user").document(auth.uid!!)
            .collection("cart")
            .addSnapshotListener { value, error -> //check cart values every refresh, updates bottomBar too
                if (error != null) {
                    viewModelScope.launch { _cartProducts.emit(Resource.Error(error.message.toString())) }
                }
                else if (value == null || value.isEmpty) {
                    cartProductDocuments = emptyList()
                    viewModelScope.launch { _cartProducts.emit(Resource.Success(emptyList())) }
                }
                else {
                    cartProductDocuments = value.documents
                    val cartProductsList = value.toObjects(CartProduct::class.java)
                    viewModelScope.launch { _cartProducts.emit(Resource.Success(cartProductsList)) }
                }
            }
    }

    fun changeQuantity(
        cartProduct: CartProduct,
        quantityChanging: FirebaseCommon.QuantityChanging
    ) {
        val updatedSnapshot = cartProductDocuments.find { snapshot ->
            val snapshotProduct = snapshot.toObject(CartProduct::class.java)
            snapshotProduct?.product?.id == cartProduct.product.id
        }

        if (updatedSnapshot != null) {
            val documentId = updatedSnapshot.id
            when(quantityChanging) {
                FirebaseCommon.QuantityChanging.INCREASE -> {
                    viewModelScope.launch { _cartProducts.emit(Resource.Loading()) }
                    increaseQuantity(documentId)
                }
                FirebaseCommon.QuantityChanging.DECREASE -> {
                    if (cartProduct.quantity == 1) {
                        viewModelScope.launch { _deleteDialog.emit(cartProduct) }
                        return
                    }
                    viewModelScope.launch { _cartProducts.emit(Resource.Loading()) }
                    decreaseQuantity(documentId)
                }
            }
        } else {
            viewModelScope.launch {
                _cartProducts.emit(Resource.Error("Product not found in cart. Please refresh."))
            }
        }
    }

    private fun decreaseQuantity(documentId: String) {
        firebaseCommon.decreaseQuantity(documentId) { _, exception ->
            if (exception != null)
                viewModelScope.launch { _cartProducts.emit(Resource.Error(exception.message.toString())) }
        }
    }

    private fun increaseQuantity(documentId: String) {
        firebaseCommon.increaseQuantity(documentId) { _, exception ->
            if (exception != null)
                viewModelScope.launch { _cartProducts.emit(Resource.Error(exception.message.toString())) }
        }
    }


}