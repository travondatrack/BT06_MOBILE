package com.example.slide_images_with_sliderview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0

    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            if (::viewPager.isInitialized) {
                val itemCount = viewPager.adapter?.itemCount ?: 0
                if (itemCount > 0) {
                    currentPage = (currentPage + 1) % itemCount
                    viewPager.currentItem = currentPage
                    handler.postDelayed(this, 3000)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewPager = findViewById(R.id.viewPager)

        val imageList = listOf(
            R.drawable.coffee1,
            R.drawable.coffee2,
            R.drawable.coffee3,
            R.drawable.coffee4
        )

        val adapter = SliderAdapter(imageList)
        viewPager.adapter = adapter


        // Start auto-scroll
        handler.postDelayed(autoScrollRunnable, 3000)
    }

    override fun onDestroy() {
        handler.removeCallbacks(autoScrollRunnable)
        super.onDestroy()
    }
}