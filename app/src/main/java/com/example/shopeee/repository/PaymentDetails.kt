package com.example.shopeee.repository

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentDetails(
    val orderId: String,
    val amount: Long,
    val currency: String
) : Parcelable {
    constructor() : this("", 0L, "sgd")
}