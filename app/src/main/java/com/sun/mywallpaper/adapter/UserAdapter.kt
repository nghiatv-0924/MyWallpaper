package com.sun.mywallpaper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sun.mywallpaper.R
import com.sun.mywallpaper.base.BaseRecyclerAdapter
import com.sun.mywallpaper.base.OnRecyclerItemClickListener
import com.sun.mywallpaper.data.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter : BaseRecyclerAdapter<User, UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(itemView, listener)
    }

    class ViewHolder(
        itemView: View,
        listener: OnRecyclerItemClickListener<User>
    ) : BaseRecyclerAdapter.ViewHolder<User>(itemView, listener) {

        override fun bindData(item: User) {
            super.bindData(item)
            itemView.apply {
                Glide.with(this)
                    .load(item.profileImage.large)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageUser)
                textUserName.text = item.name
                textUserUsername.text = item.username
            }
        }
    }
}
