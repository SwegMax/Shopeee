package com.example.shopeee.repository

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String,
    val name: String,
    val category: String,
    val price: Float,
    val offerPercentage: Float? = null,
    val description: String? = null,
    val colors: List<Int>? = null,
    val sizes: List<String>? = null,
    val images: List<String>
): Parcelable {
    constructor():this("0", "", "", 0f, images = emptyList())
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "category" to category,
            "price" to price,
            "offerPercentage" to offerPercentage,
            "description" to description,
            "colors" to colors,
            "sizes" to sizes,
            "images" to images
        )
    }
    companion object {
        fun fromMap(map: Map<String, Any?>): Product {
            return Product(
                id = map["id"] as String,
                name = map["name"] as String,
                category = map["category"] as String,
                price = (map["price"] as Double).toFloat(),
                offerPercentage = (map["offerPercentage"] as? Double)?.toFloat(),
                description = map["description"] as? String,
                colors = (map["colors"] as? List<Int>),
                sizes = (map["sizes"] as? List<String>),
                images = (map["images"] as List<String>)
            )
        }
    }
}