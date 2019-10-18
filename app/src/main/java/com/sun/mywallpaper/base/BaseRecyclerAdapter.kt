package com.sun.mywallpaper.base

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T, VH : BaseRecyclerAdapter.ViewHolder<T>>(
    private val items: MutableList<T> = mutableListOf()
) : RecyclerView.Adapter<VH>() {

    protected lateinit var listener: OnRecyclerItemClickListener<T>

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bindData(items[position])

    fun updateData(data: List<T>) =
        DiffUtil.calculateDiff(Callback(items, data)).run {
            items.clear()
            items.addAll(data)
            dispatchUpdatesTo(this@BaseRecyclerAdapter)
        }

    fun setOnRecyclerItemClickListener(listener: OnRecyclerItemClickListener<T>) {
        this.listener = listener
    }

    abstract class ViewHolder<T>(
        itemView: View,
        private val listener: OnRecyclerItemClickListener<T>
    ) : RecyclerView.ViewHolder(itemView) {

        private var item: T? = null

        init {
            itemView.setOnClickListener {
                item?.let { onHandleItemClick(it) }
            }
        }

        open fun bindData(item: T) {
            this.item = item
        }

        open fun onHandleItemClick(item: T) {
            listener.showItemDetail(item)
        }
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
