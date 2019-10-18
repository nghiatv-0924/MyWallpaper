package com.sun.mywallpaper.base

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T, VH : BaseRecyclerAdapter.ViewHolder<T>>(
    private val items: MutableList<T> = mutableListOf()
) : RecyclerView.Adapter<VH>() {

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bindData(items[position])

    fun updateData(data: List<T>) =
        DiffUtil.calculateDiff(Callback(items, data)).run {
            items.clear()
            items.addAll(data)
            dispatchUpdatesTo(this@BaseRecyclerAdapter)
        }

    abstract class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bindData(item: T)
    }

    class Callback<T>(
        private val oldData: List<T>,
        private val newData: List<T>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldData.size

        override fun getNewListSize() = newData.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldData[oldItemPosition] == newData[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            areItemsTheSame(oldItemPosition, newItemPosition)
    }
}
