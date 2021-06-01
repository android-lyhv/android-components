package com.lyhv.component.common

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * RecyclerViewで使用するViewHolderのベースクラス
 */
abstract class BaseViewHolder<B : ViewBinding, T>(
    val context: Context,
    val binding: B
) :
    RecyclerView.ViewHolder(binding.root) {
    val view: View get() = binding.root
    abstract fun onBind(item: T)
}