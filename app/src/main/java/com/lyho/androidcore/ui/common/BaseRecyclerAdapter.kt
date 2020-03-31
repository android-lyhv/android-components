package com.lyho.androidcore.ui.common

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T, VH : RecyclerView.ViewHolder>(private val diffUtil: RecyclerDiffUtilCallBack<T>) :
    RecyclerView.Adapter<VH>() {
    private val items = mutableListOf<T>()
    fun getItems(): List<T> {
        return items
    }
    fun getItem(adapterPosition: Int): T{
        return items[adapterPosition]
    }

    fun setItems(newItems: List<T>) {
        val diffResult = DiffUtil.calculateDiff(diffUtil.apply {
            applyItems(items, newItems)
        })
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }


    override fun getItemCount(): Int {
        return items.size
    }
}
