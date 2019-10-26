package com.sun.mywallpaper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.mywallpaper.R
import com.sun.mywallpaper.base.BaseRecyclerAdapter
import com.sun.mywallpaper.base.OnRecyclerItemClickListener
import com.sun.mywallpaper.util.Constants
import com.sun.mywallpaper.util.Utils
import ja.burhanrashid52.photoeditor.PhotoFilter
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterAdapter : BaseRecyclerAdapter<Pair<String, PhotoFilter>, FilterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
        return ViewHolder(itemView, listener)
    }

    class ViewHolder(
        itemView: View,
        listener: OnRecyclerItemClickListener<Pair<String, PhotoFilter>>
    ) : BaseRecyclerAdapter.ViewHolder<Pair<String, PhotoFilter>>(itemView, listener) {

        override fun bindData(item: Pair<String, PhotoFilter>) {
            super.bindData(item)
            itemView.apply {
                val fromAsset = Utils.getBitmapFromAsset(context, item.first)
                imageFilter.setImageBitmap(fromAsset)
                textFilter.text = item.second.name.replace(
                    Constants.UNDER_SCORE_CHARACTER,
                    Constants.SPACE_CHARACTER
                )
            }
        }
    }
}
