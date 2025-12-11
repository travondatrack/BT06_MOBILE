package com.example.recycleview_indicator_search

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LinePagerIndicatorDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val DP: Float = context.resources.displayMetrics.density

    private val selectedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = 0xFFFFFFFF.toInt()
    }
    private val unselectedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = 0x66FFFFFF
    }

    private val indicatorRadius = (4f * DP)
    private val indicatorPadding = (8f * DP)
    private val indicatorYOffset = (12f * DP)

    fun setSelectedColor(@ColorInt color: Int) {
        selectedPaint.color = color
    }

    fun setUnselectedColor(@ColorInt color: Int) {
        unselectedPaint.color = color
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val itemCount = parent.adapter?.itemCount ?: 0
        if (itemCount == 0) return

        val width = parent.width
        val paddingLeft = parent.paddingLeft
        val paddingRight = parent.paddingRight

        val totalWidth = itemCount * (indicatorRadius * 2f) + (itemCount - 1) * indicatorPadding
        val startX = paddingLeft + (width - paddingLeft - paddingRight - totalWidth) / 2f
        val centerY = parent.height - indicatorYOffset

        val lm = parent.layoutManager
        var activePos = 0
        if (lm is LinearLayoutManager) {
            activePos = lm.findFirstVisibleItemPosition()
            if (activePos == RecyclerView.NO_POSITION) activePos = 0
        }

        for (i in 0 until itemCount) {
            val cx = startX + i * (indicatorRadius * 2f + indicatorPadding) + indicatorRadius
            val p = if (i == activePos) selectedPaint else unselectedPaint
            c.drawCircle(cx, centerY, indicatorRadius, p)
        }
    }
}

