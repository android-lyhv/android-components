package com.lyhv.component.common

import androidx.recyclerview.widget.DiffUtil

abstract class RecyclerDiffUtilCallBack<T> :
    DiffUtil.Callback() {
    private var oldItems = listOf<T>()
    private var newItems = listOf<T>()
    fun applyItems(oldItems: List<T>, newItems: List<T>) {
        this.oldItems = oldItems
        this.newItems = newItems
    }

    fun getOldItemPosition(oldItemPosition: Int): T {
        return oldItems[oldItemPosition]
    }

    fun getNewItemPosition(newItemPosition: Int): T {
        return newItems[newItemPosition]
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return sameItem(oldItemPosition, newItemPosition)
    }

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return sameContent(oldItemPosition, newItemPosition)
    }

    abstract fun sameItem(oldItemPosition: Int, newItemPosition: Int): Boolean
    abstract fun sameContent(oldItemPosition: Int, newItemPosition: Int): Boolean
}