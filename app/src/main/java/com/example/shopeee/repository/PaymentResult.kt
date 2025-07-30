package com.example.shopeee.repository

sealed class PaymentResult {
    data object Completed : PaymentResult()
    data object Canceled : PaymentResult()
    data class Failed(val message: String?) : PaymentResult()
    data object Unspecified : PaymentResult()
}