package com.example.slide_images_with_viewflipper

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide

class ViewFlipperActivity : ComponentActivity() {
    private lateinit var viewFlipperMain: ViewFlipper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_flipper)

        viewFlipperMain = findViewById(R.id.viewFlipperMain)

        // Populate and configure the ViewFlipper
        actionViewFlipperMain()
    }

    private fun actionViewFlipperMain() {
        // Use the Pixabay direct image URLs provided by the user
        val arrayListFlipper = ArrayList<String>()
        arrayListFlipper.add("https://cdn.pixabay.com/photo/2016/02/18/15/23/mountain-ranges-1207334_1280.jpg")
        arrayListFlipper.add("https://cdn.pixabay.com/photo/2020/12/23/08/00/bow-lake-5854210_1280.jpg")
        arrayListFlipper.add("https://cdn.pixabay.com/photo/2019/12/06/18/47/bavarian-forest-4677982_1280.jpg")

        for (url in arrayListFlipper) {
            val imageView = ImageView(this@ViewFlipperActivity)
            // Ensure the child fills the ViewFlipper area
            imageView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            imageView.setBackgroundColor(Color.LTGRAY) // fallback background while loading
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP

            // Use activity context (lifecycle-aware) and add placeholder/error drawables
            Glide.with(this@ViewFlipperActivity)
                .load(url)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error)
                .into(imageView)

            viewFlipperMain.addView(imageView)
        }

        viewFlipperMain.flipInterval = 3000
        viewFlipperMain.isAutoStart = true

        val slideIn: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        val slideOut: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_out_right)
        viewFlipperMain.inAnimation = slideIn
        viewFlipperMain.outAnimation = slideOut
    }
}
