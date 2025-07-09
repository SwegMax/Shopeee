package com.example.shopeee.helper

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.shopeee.R
import com.facebook.shimmer.ShimmerFrameLayout

class ShimmerImgHelper(
    private val shimmerContainer: ShimmerFrameLayout,
    private val actualImage: ImageView
) {

    fun showShimmer() {
        shimmerContainer.visibility = View.VISIBLE
        actualImage.visibility = View.GONE
    }

    fun hideShimmer() {
        shimmerContainer.stopShimmer()
        shimmerContainer.visibility = View.GONE
        actualImage.visibility = View.VISIBLE //seperate th eimage and shimmer container
    }

    fun loadImageWithShimmer(imgUrl: String?) {
        showShimmer()

        if (imgUrl.isNullOrEmpty()) {
            Glide.with(actualImage.context)
                .load(ResourcesCompat.getDrawable(actualImage.context.resources,
                    R.drawable.alpha_s_box, null))
                .into(actualImage)
            hideShimmer()
            return
        }

        Glide.with(actualImage.context)
            .load(imgUrl)
            .into(object : SimpleTarget<Drawable>() {
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    actualImage.setImageDrawable(resource)
                    hideShimmer()
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    actualImage.setImageDrawable(errorDrawable)
                    hideShimmer()
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    actualImage.setImageDrawable(placeholder)
                }
            })
    }
}