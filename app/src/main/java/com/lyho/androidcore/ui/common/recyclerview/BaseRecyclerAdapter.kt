package com.lyho.androidcore.ui.common.recyclerview

import android.content.Context
import androidx.recyclerview.widget.RecyclerView

/**
 * Base Adapter.
 */
abstract class BaseRecyclerAdapter<T, VH : BaseViewHolder>(protected val context: Context) :
    RecyclerView.Adapter<VH>() {
    private val mElements: MutableList<T> = ArrayList()
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind()
    }

    fun addElements(elements: List<T>?) {
        if (elements?.isNotEmpty() == true) {
            mElements.addAll(elements)
            notifyItemRangeInserted(itemCount, elements.size)
        }
    }

    fun setElements(elements: List<T>?) {
        elements?.let {
            mElements.clear()
            mElements.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun removeElement(position: Int) {
        mElements.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getElement(position: Int): T? {
        return try {
            mElements[position]
        } catch (e: Exception) {
            null
        }
    }

    fun getElements(): List<T> {
        return mElements
    }

    override fun getItemCount(): Int {
        return mElements.size
    }
}
