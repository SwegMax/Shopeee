package com.example.shopeee.viewmodelMVVM

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.shopeee.repository.Product
import com.example.shopeee.repository.Resource
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
): ViewModel() {
    private var lastProduct : DocumentSnapshot? = null
    private var currentQuery: String? = null

    suspend fun startSearch(query: String?): Resource<List<Product>> {
        currentQuery = query
        return query?.let {
            try {
                val baseQuery = firestore.collection("Products")
                    .whereGreaterThanOrEqualTo("name", it)
                    .orderBy("name")
                    .limit(10)

                val result = baseQuery.get().await()
                lastProduct = result.documents.lastOrNull()
                val searchProducts = result.toObjects(Product::class.java)

                Resource.Success(searchProducts)
            } catch (e: Exception) {
                Resource.Error(e.message.toString())
            }
        } ?: Resource.Success(emptyList())
    }

    suspend fun loadNextPage(): Resource<List<Product>> {
        return try {
            val startAfter = lastProduct ?: return Resource.Success(emptyList())
            val query = currentQuery ?: return Resource.Success(emptyList())

            val nextPageQuery = firestore.collection("Products")
                .whereGreaterThanOrEqualTo("name", query)
                .orderBy("name")
                .startAfter(startAfter)
                .limit(10)

            val result = nextPageQuery.get().await()
            lastProduct = result.documents.lastOrNull()
            val newProducts = result.toObjects(Product::class.java)

            Resource.Success(newProducts)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}