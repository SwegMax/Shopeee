package com.example.shopeee.dInjection

import androidx.activity.ComponentActivity
import com.example.shopeee.external.GPayService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object PaymentsModule {

    @Provides
    @ActivityScoped
    fun providePaymentsService(activity: ComponentActivity): GPayService {
        return GPayService(activity)
    }
}