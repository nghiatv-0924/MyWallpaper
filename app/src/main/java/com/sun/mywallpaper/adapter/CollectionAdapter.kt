package com.sun.mywallpaper.adapter

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.sun.mywallpaper.R
import com.sun.mywallpaper.base.BaseRecyclerAdapter
import com.sun.mywallpaper.base.OnRecyclerItemClickListener
import com.sun.mywallpaper.data.model.Collection
import kotlinx.android.synthetic.main.item_collection.view.*

class CollectionAdapter : BaseRecyclerAdapter<Collection, CollectionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_collection, parent, false)
        return ViewHolder(itemView, listener)
    }

    class ViewHolder(
        itemView: View,
        listener: OnRecyclerItemClickListener<Collection>
    ) : BaseRecyclerAdapter.ViewHolder<Collection>(itemView, listener) {

        @SuppressLint("SetTextI18n")
        override fun bindData(item: Collection) {
            super.bindData(item)
            itemView.apply {
                Glide.with(context)
                    .load(item.coverPhoto.urls.regular)
                    .transition(GenericTransitionOptions.with { view ->
                        val fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
                        fadeAnim.duration = 500
                        fadeAnim.start()
                    })
                    .into(imageCollection)
                imageCollectionPrivate.visibility = if (item.isPrivate) View.VISIBLE else View.GONE
                textCollectionName.text = item.title
                textCollectionSize.text = "${item.totalPhotos} photos"
            }
        }
    }
}
