package com.lyhv.mvvm.common

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

@Suppress("unused")

open class DataBindingViewHolder<Binding : ViewDataBinding>(var binding: Binding) :

    RecyclerView.ViewHolder(binding.root) {

    protected val context: Context
        get() = binding.root.context

    constructor(parent: ViewGroup, layout: Int) : this(
        LayoutInflater.from(parent.context),
        parent,
        layout
    )

    constructor(
        inflater: LayoutInflater,
        parent: ViewGroup,
        layout: Int
    ) : this(DataBindingUtil.inflate<Binding>(inflater, layout, parent, false))

}
