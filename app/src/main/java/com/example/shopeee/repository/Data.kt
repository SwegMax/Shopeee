package com.example.shopeee.repository

data class Item(
        var itemImage : Int,
        var heading : String,
        var id : Long,
        var quantity: Int)

data class User(
        val userId: String = "",
        val username: String,
        var items: List<Item> = emptyList()
)