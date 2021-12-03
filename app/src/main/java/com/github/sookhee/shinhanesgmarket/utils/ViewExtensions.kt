package com.github.sookhee.shinhanesgmarket.utils

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
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

@SuppressLint("CheckResult")
fun ImageView.setImageWithUrl(url: String, radius: Int = 0) {
    val options = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE) // 디스크 캐시 리소스
        .skipMemoryCache(true) // 메모리 캐시 사용안함.
        .priority(Priority.HIGH) // 처리순서
        .centerCrop()

    if (radius != 0) options.transform(RoundedCorners(radius))

    Glide.with(context)
        .load(url)
        .apply(options)
        .error(R.drawable.gray_border_background)
        .into(this)
}
