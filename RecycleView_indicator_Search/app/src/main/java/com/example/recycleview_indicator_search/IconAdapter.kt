package com.example.recycleview_indicator_search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IconAdapterKt(
    private val context: Context,
    private var arrayList: List<IconModel>?
) : RecyclerView.Adapter<IconAdapterKt.IconHolder>() {

    class IconHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivImgIcon: ImageView = itemView.findViewById(R.id.ivImgIcon)
        val tvIcon: TextView = itemView.findViewById(R.id.tvIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_icon_promotion, parent, false)
        return IconHolder(view)
    }

    override fun onBindViewHolder(holder: IconHolder, position: Int) {
        val item = arrayList?.get(position)
        if (item != null) {
            item.imgId?.let { holder.ivImgIcon.setImageResource(it) }
            holder.tvIcon.text = item.desc ?: ""
        } else {
            holder.ivImgIcon.setImageDrawable(null)
            holder.tvIcon.text = ""
        }
    }

    override fun getItemCount(): Int = arrayList?.size ?: 0

    fun updateData(newList: List<IconModel>?) {
        arrayList = newList
        notifyDataSetChanged()
    }

    fun setListenerList(iconModelList: List<IconModel>?) {
        this.arrayList = iconModelList
        notifyDataSetChanged()
    }
}
