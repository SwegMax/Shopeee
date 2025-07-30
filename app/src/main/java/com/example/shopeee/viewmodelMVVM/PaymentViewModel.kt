package com.example.shopeee.viewmodelMVVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopeee.repository.PaymentDetails
import com.example.shopeee.repository.PaymentResult
import com.example.shopeee.repository.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _paymentResult = MutableStateFlow<Resource<PaymentResult>>(Resource.Unspecified())
    val paymentResult = _paymentResult.asStateFlow()

    private val functions: FirebaseFunctions by lazy { Firebase.functions }

    fun initiateGooglePayPayment(paymentDetails: PaymentDetails) {
        viewModelScope.launch {
            _paymentResult.emit(Resource.Loading())
            try {
                val data = hashMapOf(
                    "amount" to paymentDetails.amount,
                    "currency" to paymentDetails.currency,
                    "orderId" to paymentDetails.orderId
                )

                val result = functions
                    .getHttpsCallable("createPaymentIntent")
                    .call(data)
                    .await()

                val clientSecret = (result.data as? Map<*, *>)?.get("clientSecret")

                if (clientSecret != null) {

                } else {
                    _paymentResult.emit(Resource.Error("Failed to get client secret from backend."))
                }
            } catch (e: Exception) {
                _paymentResult.emit(Resource.Error("Error during PaymentIntent creation: ${e.message}"))
            }
        }
    }

    fun handleGooglePayResult(result: PaymentResult) {
        viewModelScope.launch {
            when (result) {
                is PaymentResult.Completed -> {
                    _paymentResult.emit(Resource.Success(result))
                }
                is PaymentResult.Canceled -> {
                    _paymentResult.emit(Resource.Error("Payment was canceled by the user."))
                }
                is PaymentResult.Failed -> {
                    _paymentResult.emit(Resource.Error("Payment failed: ${result.message}"))
                }
                else -> {}
            }
        }
    }

    fun finalizeOrderAfterPayment(orderId: String) {
        viewModelScope.launch {

        }
    }
}