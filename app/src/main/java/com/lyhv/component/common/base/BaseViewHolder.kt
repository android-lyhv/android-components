package com.lyhv.component.common.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T: ViewDataBinding>(view: View): RecyclerView.ViewHolder(view){
    val binding : T = DataBindingUtil.bind(view)!!
}