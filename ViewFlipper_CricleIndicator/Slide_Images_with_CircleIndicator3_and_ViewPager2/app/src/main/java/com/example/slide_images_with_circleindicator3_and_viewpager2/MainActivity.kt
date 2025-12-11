package com.example.slide_images_with_circleindicator3_and_viewpager2

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3
import com.example.slide_images_with_circleindicator3_and_viewpager2.ui.ImagesViewPager2Adapter
import com.example.slide_images_with_circleindicator3_and_viewpager2.ui.DepthPageTransformer

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var circleIndicator3: CircleIndicator3
    private lateinit var imagesList1: List<Images>

    private val autoScrollInterval = 3000L // 3 seconds
    private val handler = Handler(Looper.getMainLooper())
    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            if (::viewPager2.isInitialized && imagesList1.isNotEmpty()) {
                val next = if (viewPager2.currentItem == imagesList1.size - 1) 0 else viewPager2.currentItem + 1
                viewPager2.setCurrentItem(next, true)
            }
            handler.postDelayed(this, autoScrollInterval)
        }
    }

    private lateinit var pageChangeCallback: ViewPager2.OnPageChangeCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)

        // ánh xạ viewpager2
        viewPager2 = findViewById(R.id.viewpage2)
        circleIndicator3 = findViewById(R.id.circle_indicator3)

        // lấy danh sách ảnh
        imagesList1 = getListImages()

        // set adapter
        val adapter1 = ImagesViewPager2Adapter(imagesList1)
        viewPager2.adapter = adapter1

        // Liên kết viewpager2 và indicator3
        circleIndicator3.setViewPager(viewPager2)

        // Reset auto-scroll timer when user manually selects a page
        pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(autoScrollRunnable)
                handler.postDelayed(autoScrollRunnable, autoScrollInterval)
            }
        }
        viewPager2.registerOnPageChangeCallback(pageChangeCallback)

        // Thiết lập hiệu ứng chuyển trang cho ViewPager2
        // Available transformers: DepthPageTransformer, ZoomOutPageTransformer
        viewPager2.setPageTransformer(DepthPageTransformer())
    }

    override fun onResume() {
        super.onResume()
        startAutoScroll()
    }

    override fun onPause() {
        stopAutoScroll()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cleanup to avoid memory leaks
        stopAutoScroll()
        if (::viewPager2.isInitialized && ::pageChangeCallback.isInitialized) {
            viewPager2.unregisterOnPageChangeCallback(pageChangeCallback)
        }
    }

    private fun startAutoScroll() {
        handler.removeCallbacks(autoScrollRunnable)
        handler.postDelayed(autoScrollRunnable, autoScrollInterval)
    }

    private fun stopAutoScroll() {
        handler.removeCallbacks(autoScrollRunnable)
    }

    private fun getListImages(): List<Images> {
        val list = ArrayList<Images>()
        // Assumes these drawable resources exist in the project
        list.add(Images(R.drawable.coffee1))
        list.add(Images(R.drawable.coffee2))
        list.add(Images(R.drawable.coffee3))
        list.add(Images(R.drawable.coffee4))
        return list
    }

    // If you also want to support legacy ViewPager (androidx.viewpager.widget.ViewPager),
    // add the dependency `implementation("androidx.viewpager:viewpager:1.0.0")` to your build.gradle
    // and then you can copy the same runnable logic for a ViewPager instance:
    //
    // private var viewPager: ViewPager? = null
    // viewPager = findViewById(R.id.viewPager)
    // in runnable: check viewPager?.currentItem and call viewPager?.setCurrentItem(...)
}