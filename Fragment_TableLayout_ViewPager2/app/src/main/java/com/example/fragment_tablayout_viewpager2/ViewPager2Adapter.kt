package com.example.fragment_tablayout_viewpager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> com.example.fragment_tablayout_viewpager2.NewOrderFragment()
            1 -> com.example.fragment_tablayout_viewpager2.PickupFragment()
            2 -> com.example.fragment_tablayout_viewpager2.DeliveryFragment()
            3 -> com.example.fragment_tablayout_viewpager2.ReviewFragment()
            4 -> com.example.fragment_tablayout_viewpager2.CancelFragment()
            else -> com.example.fragment_tablayout_viewpager2.NewOrderFragment()
        }
    }

    override fun getItemCount(): Int = 5
}
