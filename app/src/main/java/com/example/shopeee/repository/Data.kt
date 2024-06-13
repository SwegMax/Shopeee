package com.example.shopeee.repository

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Item(
        var itemImage : Int,
        var heading : String,
        var id : Long,
        var quantity: Int)

/*data class User(
        val userId: String = "",
        val username: String,
        var items: List<Item> = emptyList()
)*/

@Parcelize
data class User(
        var firstName:String,
        var lastName:String,
        var email:String,
        var imagePath:String=""
): Parcelable {
    constructor() : this("","","")
}