package com.example.shopeee.external

import androidx.activity.ComponentActivity
import com.stripe.android.googlepaylauncher.GooglePayEnvironment
import com.stripe.android.googlepaylauncher.GooglePayLauncher
import com.example.shopeee.repository.PaymentResult
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class GPayService @Inject constructor(
    private val activity: ComponentActivity
) {

    private lateinit var googlePayLauncher: GooglePayLauncher
    private var readyCallback: ((Boolean) -> Unit)? = null
    private var resultCallback: ((PaymentResult) -> Unit)? = null

    fun initialize(
        publishableKey: String,
        environment: GooglePayEnvironment,
        merchantCountryCode: String,
        merchantName: String,
        onReady: (Boolean) -> Unit,
        onResult: (PaymentResult) -> Unit
    ) {
        this.readyCallback = onReady
        this.resultCallback = onResult

        googlePayLauncher = GooglePayLauncher(
            activity = activity,
            config = GooglePayLauncher.Config(
                environment = environment,
                merchantCountryCode = merchantCountryCode,
                merchantName = merchantName
            ),
            readyCallback = { isReady -> this.readyCallback?.invoke(isReady) },
            resultCallback = { googlePayResult ->
                val paymentResult: PaymentResult = when (googlePayResult) {
                    is GooglePayLauncher.Result.Completed -> PaymentResult.Completed
                    is GooglePayLauncher.Result.Canceled -> PaymentResult.Canceled
                    is GooglePayLauncher.Result.Failed -> PaymentResult.Failed("Failed")
                }
                this.resultCallback?.invoke(paymentResult)
            }
        )
    }

    fun presentGooglePay(clientSecret: String) {
        if (::googlePayLauncher.isInitialized) {
            googlePayLauncher.presentForPaymentIntent(clientSecret)
        } else {
            resultCallback?.invoke(
                PaymentResult.Failed("GooglePayLauncher not initialized. Call initialize() first.")
            )
        }
    }
}