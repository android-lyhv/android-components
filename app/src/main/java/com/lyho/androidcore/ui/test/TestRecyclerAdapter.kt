package com.lyho.androidcore.ui.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lyho.androidcore.R

import com.lyho.androidcore.ui.common.recyclerview.BaseRecyclerAdapter
import com.lyho.androidcore.ui.common.recyclerview.BaseViewHolder
import com.lyho.androidcore.ui.common.recyclerview.OnBlockViewClickListener

/**
 * Created by lyhv on August 29, 2019
 * Copyright @ est-rouge. All rights reserved
 */
class TestRecyclerAdapter(context: Context) : BaseRecyclerAdapter<String, BaseViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return TestViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class TestViewHolder(itemView: View) : BaseViewHolder(itemView),
        OnBlockViewClickListener {

        override fun onBind() {
            // TODO
        }

    }
}
