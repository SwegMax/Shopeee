package com.example.shopeee.repository

import android.content.Context
import android.view.animation.Animation
import com.example.shopeee.R

object AnimationUtils {

    fun loadingShake(context: Context?): Animation {
        return android.view.animation.AnimationUtils.loadAnimation(context, R.anim.shake_loading)
    }

    fun successShake(context: Context?): Animation {
        return android.view.animation.AnimationUtils.loadAnimation(context, R.anim.shake_success)
    }

    fun errorShake(context: Context?): Animation {
        return android.view.animation.AnimationUtils.loadAnimation(context, R.anim.shake_error)
    }


    //different shakes Loading, Success, Error

}