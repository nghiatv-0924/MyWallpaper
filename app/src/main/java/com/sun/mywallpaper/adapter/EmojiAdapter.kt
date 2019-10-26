package com.sun.mywallpaper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sun.mywallpaper.R
import com.sun.mywallpaper.base.BaseRecyclerAdapter
import com.sun.mywallpaper.base.OnRecyclerItemClickListener
import kotlinx.android.synthetic.main.item_emoji.view.*

class EmojiAdapter : BaseRecyclerAdapter<String, EmojiAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_emoji, parent, false)
        return ViewHolder(itemView, listener)
    }

    class ViewHolder(
        itemView: View,
        listener: OnRecyclerItemClickListener<String>
    ) : BaseRecyclerAdapter.ViewHolder<String>(itemView, listener) {

        override fun bindData(item: String) {
            super.bindData(item)
            itemView.textEmoji.text = item
        }
    }
}
