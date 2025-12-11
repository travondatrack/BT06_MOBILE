package com.example.slide_images_with_circleindicator_and_viewpager

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import me.relex.circleindicator.CircleIndicator

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var circleIndicator: CircleIndicator
    private var imagesList: List<Images> = emptyList()

    // Auto-scroll handler and runnable
    private val AUTO_SCROLL_DELAY = 3000L
    private val handler: Handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            if (imagesList.isNotEmpty()) {
                if (viewPager.currentItem == imagesList.size - 1) {
                    viewPager.currentItem = 0
                } else {
                    viewPager.currentItem = viewPager.currentItem + 1
                }
            }
            handler.postDelayed(this, AUTO_SCROLL_DELAY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewpage)
        circleIndicator = findViewById(R.id.circle_indicator)

        imagesList = getListImages()
        val adapter = ImagesViewPageAdapter(imagesList)
        viewPager.adapter = adapter

        // Link viewpager and indicator
        circleIndicator.setViewPager(viewPager)

        // Start the runnable once to kick things off (onResume will also schedule).
        handler.postDelayed(runnable, AUTO_SCROLL_DELAY)

        // Listen for user page changes; reset the auto-scroll timer when a page is selected.
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // no-op
            }

            override fun onPageSelected(position: Int) {
                // reset auto-scroll timer when user manually changes page
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, AUTO_SCROLL_DELAY)
            }

            override fun onPageScrollStateChanged(state: Int) {
                // no-op
            }
        })
    }

    override fun onResume() {
        super.onResume()
        // start autorun
        handler.postDelayed(runnable, AUTO_SCROLL_DELAY)
    }

    override fun onPause() {
        super.onPause()
        // stop autorun to avoid leaks
        handler.removeCallbacks(runnable)
    }

    private fun getListImages(): List<Images> {
        val list = ArrayList<Images>()
        list.add(Images(R.drawable.coffee1))
        list.add(Images(R.drawable.coffee2))
        list.add(Images(R.drawable.coffee3))
        list.add(Images(R.drawable.coffee4))
        return list
    }
}