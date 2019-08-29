package com.lyho.androidcore.ui.common.recyclerview

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
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
    (
    context: Context,
    private val orientation: Int,
    private val mSpacing: Int = 0,
    private val drawableResourceId: Int? = null
) :
    DividerItemDecoration(context, orientation) {
    init {
        drawableResourceId?.let { it ->
            val drawable = ContextCompat.getDrawable(context, it)
            drawable?.let {
                setDrawable(it)
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = mSpacing
        outRect.right = mSpacing
        outRect.top = mSpacing
        outRect.bottom = mSpacing
    }
}
