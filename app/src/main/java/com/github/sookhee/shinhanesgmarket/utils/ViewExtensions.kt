package com.github.sookhee.shinhanesgmarket.utils

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.sookhee.shinhanesgmarket.R

fun View.heightAnimation(targetHeight: Int, duration: Long = 100L) {
    val prevHeight: Int = this.height
    val valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight)
    valueAnimator.addUpdateListener { animation ->
        this.layoutParams.height = animation.animatedValue as Int
        this.requestLayout()
    }
    valueAnimator.interpolator = DecelerateInterpolator()
    valueAnimator.duration = duration
    valueAnimator.start()
}

fun View.setGone() {
    this.visibility = View.GONE
}

fun View.setVisible() {
    this.visibility = View.VISIBLE
}

fun ImageView.setImageWithUrl(url: String) {
    Glide.with(context)
        .load(url)
        .error(R.drawable.gray_border_background)
        .into(this)
}
