package com.example.slide_images_with_circleindicator_and_viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter

class ImagesViewPageAdapter(private val imagesList: List<Images>?) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.item_images, container, false)
        val imageView: ImageView = view.findViewById(R.id.imgView)
        val images = imagesList?.get(position)
        images?.let {
            imageView.setImageResource(it.imagesId)
        }
        container.addView(view)
        return view
    }

    override fun getCount(): Int = imagesList?.size ?: 0

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}

