package com.sun.mywallpaper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.mywallpaper.R
import com.sun.mywallpaper.base.BaseRecyclerAdapter
import com.sun.mywallpaper.base.OnRecyclerItemClickListener
import kotlinx.android.synthetic.main.item_color.view.*

class ColorPickerAdapter : BaseRecyclerAdapter<Int, ColorPickerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        return ViewHolder(itemView, listener)
    }

    class ViewHolder(
        itemView: View,
        listener: OnRecyclerItemClickListener<Int>
    ) : BaseRecyclerAdapter.ViewHolder<Int>(itemView, listener) {

        override fun bindData(item: Int) {
            super.bindData(item)
            itemView.viewColorPicker.setBackgroundColor(item)
        }
    }
}
