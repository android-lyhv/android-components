package com.lyho.androidcore.ui.common.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Base Holder.
 *
 */
abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind()
}
