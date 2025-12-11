package com.example.recycleview_indicator_search

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var rcIcon: RecyclerView
    private lateinit var iconAdapter: IconAdapterKt
    private lateinit var arrayList1: ArrayList<IconModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rcIcon = findViewById(R.id.rclIcon)

        // Prepare data list
        arrayList1 = ArrayList()
        arrayList1.add(IconModel(R.drawable.icon1, "hamburger"))
        arrayList1.add(IconModel(R.drawable.icon2, "pizza slice"))
        arrayList1.add(IconModel(R.drawable.icon3, "hotdog"))
        arrayList1.add(IconModel(R.drawable.icon4, "cocacola"))
        arrayList1.add(IconModel(R.drawable.icon5, "donut"))
        arrayList1.add(IconModel(R.drawable.icon6, "speaker"))
        arrayList1.add(IconModel(R.drawable.icon7, "gmail"))
        arrayList1.add(IconModel(R.drawable.icon8, "facebook"))
        arrayList1.add(IconModel(R.drawable.icon9, "google"))

        rcIcon.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        iconAdapter = IconAdapterKt(this, arrayList1)
        rcIcon.adapter = iconAdapter


        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterListener(newText ?: "")
                return true
            }
        })
    }

    private fun filterListener(text: String) {
        if (text.isBlank()) {
            iconAdapter.setListenerList(arrayList1)
            return
        }

        val list = ArrayList<IconModel>()
        val lower = text.lowercase(Locale.getDefault())
        for (iconModel in arrayList1) {
            val desc = iconModel.desc ?: continue
            if (desc.lowercase(Locale.getDefault()).contains(lower)) {
                list.add(iconModel)
            }
        }

        if (list.isEmpty()) {
            Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show()
        } else {
            iconAdapter.setListenerList(list)
        }
    }
}