package com.lyhv.mvvm.widget

/**
 * Created by Ly Ho V. on October 07, 2018
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */

import android.content.Context
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.View
import com.lyhv.mvvm.R
import kotlin.math.abs

class CarouselViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ViewPager(context, attrs), ViewPager.PageTransformer {
    private var mPageMargin: Int = 0
    private var mMaxTranslateOffsetX = 0
    private var mIsInitConfig = false

    init {
        clipToPadding = false
        clipChildren = false
        setPageTransformer(false, this)
        mPageMargin = context.resources.getDimensionPixelSize(R.dimen.page_marrgin)
        mMaxTranslateOffsetX = context.resources.getDimensionPixelSize(R.dimen.page_offset)
        setPadding(mPageMargin, mPageMargin, mPageMargin, mPageMargin)
    }

    override fun transformPage(view: View, position: Float) {
        if (!mIsInitConfig) {
            mIsInitConfig = true
            return
        }
        val leftInScreen = view.left - scrollX
        val centerXInViewPager = leftInScreen + view.measuredWidth / 2
        val offsetX = centerXInViewPager - measuredWidth / 2
        val offsetRate = offsetX.toFloat() * 0.3f / measuredWidth
        val scaleFactor = 1 - abs(offsetRate)

        if (scaleFactor > 0) {
            view.scaleX = scaleFactor
            view.scaleY = scaleFactor
            view.translationX = -mMaxTranslateOffsetX * offsetRate
            ViewCompat.setElevation(view, 0.0f)
        }
        ViewCompat.setElevation(view, scaleFactor)
    }
}