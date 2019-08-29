package com.lyho.androidcore.ui.common

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by lyhv on August 29, 2019
 * Copyright @ est-rouge. All rights reserved
 */
class DividerSpacingItemDecoration
/**
 * Creates a divider [RecyclerView.ItemDecoration] that can be used with a
 * [LinearLayoutManager].
 *
 * @param context     Current context, it will be used to access resources.
 * @param orientation Divider orientation. Should be [.HORIZONTAL] or [.VERTICAL].
 */
    (context: Context, orientation: Int, val mSpacing: Int = 0) :
    DividerItemDecoration(context, orientation) {
    private val mOrientation: Int = orientation
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildViewHolder(view).adapterPosition
        val itemCount = state.itemCount
        super.getItemOffsets(outRect, view, parent, state)
        when (mOrientation) {
            HORIZONTAL -> {
                outRect.left = mSpacing
                outRect.right = if (position == itemCount - 1) mSpacing else 0
                outRect.top = mSpacing
                outRect.bottom = mSpacing
            }
            VERTICAL -> {
                outRect.left = mSpacing
                outRect.right = mSpacing
                outRect.top = if (position == 0) mSpacing else mSpacing
                outRect.bottom = if (position == itemCount - 1) mSpacing else 0
            }
        }
    }
}
