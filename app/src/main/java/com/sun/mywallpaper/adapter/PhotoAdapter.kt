package com.sun.mywallpaper.adapter

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.sun.mywallpaper.R
import com.sun.mywallpaper.base.BaseRecyclerAdapter
import com.sun.mywallpaper.base.OnRecyclerItemClickListener
import com.sun.mywallpaper.data.model.Photo
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoAdapter : BaseRecyclerAdapter<Photo, PhotoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return ViewHolder(itemView, listener)
    }

    class ViewHolder(
        itemView: View,
        listener: OnRecyclerItemClickListener<Photo>
    ) : BaseRecyclerAdapter.ViewHolder<Photo>(itemView, listener) {

        override fun bindData(item: Photo) {
            super.bindData(item)
            itemView.apply {
                Glide.with(context)
                    .load(item.urls.regular)
                    .transition(GenericTransitionOptions.with { view ->
                        val fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
                        fadeAnim.duration = 500
                        fadeAnim.start()
                    })
                    .into(imagePhoto)
            }
        }
    }
}
