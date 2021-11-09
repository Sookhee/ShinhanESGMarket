package com.github.sookhee.shinhanesgmarket.utils

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator

fun View.heightAnimation(duration: Int, targetHeight: Int) {
    val prevHeight: Int = this.height
    val valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight)
    valueAnimator.addUpdateListener { animation ->
        this.layoutParams.height = animation.animatedValue as Int
        this.requestLayout()
    }
    valueAnimator.interpolator = DecelerateInterpolator()
    valueAnimator.duration = duration.toLong()
    valueAnimator.start()
}

fun View.setGone() {
    this.visibility = View.GONE
}

fun View.setVisible() {
    this.visibility = View.VISIBLE
}