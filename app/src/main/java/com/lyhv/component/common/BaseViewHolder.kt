package com.lyhv.component.common

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerViewで使用するViewHolderのベースクラス
 */
abstract class BaseViewHolder<B : ViewDataBinding, T>(
    val context: Context,
    val binding: B
) :
    RecyclerView.ViewHolder(binding.root) {
    val view: View get() = binding.root
    abstract fun onBind(item: T)
}