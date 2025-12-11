package com.example.slide_images_with_circleindicator3_and_viewpager2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.slide_images_with_circleindicator3_and_viewpager2.Images
import com.example.slide_images_with_circleindicator3_and_viewpager2.R

class ImagesViewPager2Adapter(private val imagesList: List<Images>?) : androidx.recyclerview.widget.RecyclerView.Adapter<ImagesViewPager2Adapter.ImagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_images, parent, false)
        return ImagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val images = imagesList?.get(position) ?: return
        holder.imageView.setImageResource(images.imagesId)
    }

    override fun getItemCount(): Int = imagesList?.size ?: 0

    class ImagesViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imgView)
    }
}
