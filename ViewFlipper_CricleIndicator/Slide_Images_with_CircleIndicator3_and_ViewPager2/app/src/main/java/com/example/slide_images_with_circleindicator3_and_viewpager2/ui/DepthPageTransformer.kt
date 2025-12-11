package com.example.slide_images_with_circleindicator3_and_viewpager2.ui

import android.view.View

class DepthPageTransformer : androidx.viewpager2.widget.ViewPager2.PageTransformer {
    companion object {
        private const val MIN_SCALE = 0.75f
    }

    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width

        when {
            position < -1 -> { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.alpha = 0f
            }
            position <= 0 -> { // [-1,0]
                view.alpha = 1f
                view.translationX = 0f
                view.translationZ = 0f
                view.scaleX = 1f
                view.scaleY = 1f
            }
            position <= 1 -> { // (0,1]
                view.alpha = 1 - position
                view.translationX = pageWidth * -position
                view.translationZ = -1f
                val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - kotlin.math.abs(position))
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
            }
            else -> { // (1,+Infinity]
                view.alpha = 0f
            }
        }
    }
}

class ZoomOutPageTransformer : androidx.viewpager2.widget.ViewPager2.PageTransformer {
    companion object {
        private const val MIN_SCALE = 0.85f
        private const val MIN_ALPHA = 0.5f
    }

    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width
        val pageHeight = view.height

        when {
            position < -1 -> {
                view.alpha = 0f
            }
            position <= 1 -> {
                val scaleFactor = kotlin.math.max(MIN_SCALE, 1 - kotlin.math.abs(position))
                val vertMargin = pageHeight * (1 - scaleFactor) / 2
                val horzMargin = pageWidth * (1 - scaleFactor) / 2

                if (position < 0) {
                    view.translationX = horzMargin - vertMargin / 2
                } else {
                    view.translationX = -horzMargin + vertMargin / 2
                }

                view.scaleX = scaleFactor
                view.scaleY = scaleFactor

                view.alpha = (MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA))
            }
            else -> {
                view.alpha = 0f
            }
        }
    }
}
