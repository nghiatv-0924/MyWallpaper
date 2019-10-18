package com.sun.mywallpaper.base

import androidx.recyclerview.widget.RecyclerView

abstract class LastItemListener : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager
        val adapter = recyclerView.adapter

        if (layoutManager != null && adapter != null) {
            val indexOfLastItemViewVisible = layoutManager.childCount - 1
            val lastItemViewVisible = layoutManager.getChildAt(indexOfLastItemViewVisible)
            lastItemViewVisible?.let {
                val adapterPosition = layoutManager.getPosition(it)
                if (adapterPosition == adapter.itemCount - 1) {
                    onLastItemVisible()
                }
            }
        }
    }

    abstract fun onLastItemVisible()
}
