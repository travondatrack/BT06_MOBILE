package com.example.fragment_tablayout_viewpager2

import android.os.Bundle
import android.view.View
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import androidx.viewpager2.widget.ViewPager2
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.fragment_tablayout_viewpager2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager2Adapter: ViewPager2Adapter
    private var pageChangeCallback: ViewPager2.OnPageChangeCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // toolbar
        setSupportActionBar(binding.toolBar)

        // FAB action
        binding.fabAction.setOnClickListener { view: View ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }

        // Setup ViewPager2 with adapter
        val fragmentManager = supportFragmentManager
        viewPager2Adapter = ViewPager2Adapter(fragmentManager, lifecycle)
        binding.viewPager2.adapter = viewPager2Adapter

        val tabTitles = arrayOf(
            getString(R.string.new_order_title),
            getString(R.string.pickup_title),
            getString(R.string.delivery_title),
            getString(R.string.danhgia_title),
            getString(R.string.cancel_title)
        )

        for (title in tabTitles) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(title))
        }

        // Sync TabLayout -> ViewPager2
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager2.currentItem = tab.position
                changeFabIcon(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        // Sync ViewPager2 -> TabLayout
        pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
                changeFabIcon(position)
            }
        }
        binding.viewPager2.registerOnPageChangeCallback(pageChangeCallback!!)

        changeFabIcon(binding.tabLayout.selectedTabPosition.takeIf { it >= 0 } ?: 0)

        binding.viewPager2.offscreenPageLimit = viewPager2Adapter.itemCount
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuSearch -> Toast.makeText(this, getString(R.string.menu_search) + " selected", Toast.LENGTH_SHORT).show()
            R.id.menuNewGroup -> Toast.makeText(this, getString(R.string.menu_new_group) + " selected", Toast.LENGTH_SHORT).show()
            R.id.menuBroadcast -> Toast.makeText(this, getString(R.string.menu_broadcast) + " selected", Toast.LENGTH_SHORT).show()
            R.id.menuWeb -> Toast.makeText(this, getString(R.string.menu_web) + " selected", Toast.LENGTH_SHORT).show()
            R.id.menuMessage -> Toast.makeText(this, getString(R.string.menu_message) + " selected", Toast.LENGTH_SHORT).show()
            R.id.menuSetting -> Toast.makeText(this, getString(R.string.menu_setting) + " selected", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeFabIcon(position: Int) {
        binding.fabAction.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val drawable = when (position) {
                0 -> ContextCompat.getDrawable(this, R.drawable.chat1)
                1 -> ContextCompat.getDrawable(this, R.drawable.camera1)
                2 -> ContextCompat.getDrawable(this, R.drawable.call1)
                else -> ContextCompat.getDrawable(this, R.drawable.chat1)
            }
            binding.fabAction.setImageDrawable(drawable)
            binding.fabAction.show()
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        pageChangeCallback?.let { binding.viewPager2.unregisterOnPageChangeCallback(it) }
    }
}