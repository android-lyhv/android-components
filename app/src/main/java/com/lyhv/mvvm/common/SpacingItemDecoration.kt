package com.lyhv.mvvm.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class SpacingItemDecoration @JvmOverloads constructor(
    private val mSpacing: Int,
    private val mSpanCount: Int? = 2,
    private var mDisplayMode: Int = -1
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager
        val position = parent.getChildViewHolder(view).adapterPosition
        val itemCount = state.itemCount
        if (mDisplayMode == -1) {
            mDisplayMode = resolveDisplayMode(layoutManager)
        }
        when (mDisplayMode) {
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
            GRID, STAGGERED -> {
                val newPosition = parent.getChildAdapterPosition(view) // item position
                val spanCount = mSpanCount ?: 2
                if (newPosition >= 0) {
                    val column = newPosition % spanCount // item column
                    outRect.left =
                        mSpacing - column * mSpacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = (column + 1) * mSpacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
                    if (newPosition < mSpacing) { // top edge
                        outRect.top = mSpacing
                    }
                    outRect.bottom = mSpacing // item bottom
                } else {
                    outRect.left = 0
                    outRect.right = 0
                    outRect.top = 0
                    outRect.bottom = 0
                }
            }
        }
    }

    private fun resolveDisplayMode(layoutManager: RecyclerView.LayoutManager?): Int {
        if (layoutManager is GridLayoutManager) return GRID
        if (layoutManager is StaggeredGridLayoutManager) return STAGGERED
        return if (layoutManager?.canScrollHorizontally() == true) HORIZONTAL else VERTICAL
    }

    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1
        const val GRID = 2
        const val STAGGERED = 3
    }
}
