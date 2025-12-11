package com.example.slide_images_with_circleindicator3_and_viewpager2.ui

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.slide_images_with_circleindicator3_and_viewpager2.R

class ImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView = itemView.findViewById(R.id.imgView)
}

