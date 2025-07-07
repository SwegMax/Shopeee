package com.example.shopeee.repository

data class Item(
        var itemImage : Int,
        var heading : String,
        var id : Long,
        var quantity: Int)

data class User(
        var firstName:String = "",
        var lastName:String = "",
        var email:String = "",
        var imagePath:String =""
)