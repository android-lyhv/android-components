package com.lyhv.stories_indicator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

class StoriesIndicator : View {
    var colorResSelected: Int = R.color.colorBlack
    var colorResUnSelected: Int = R.color.colorWhite
    var duration = 500
    var stockWidthRes: Int = R.dimen.padding_2dp
    var paddingRes: Int = R.dimen.padding_5dp
    var inInterpolator = LinearInterpolator()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(
        context,
        attrs
    ) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.StoriesIndicator)
        colorResSelected =
            typeArray.getResourceId(R.styleable.StoriesIndicator_colorSelect, R.color.colorBlack)
        colorResUnSelected =
            typeArray.getResourceId(R.styleable.StoriesIndicator_colorSelect, R.color.colorWhite)
        duration = typeArray.getInt(R.styleable.StoriesIndicator_duration, 500)
        stockWidthRes =
            typeArray.getResourceId(R.styleable.StoriesIndicator_strokeWidth, R.dimen.padding_2dp)
        paddingRes = typeArray.getResourceId(
            R.styleable.StoriesIndicator_indicatorPadding,
            R.dimen.padding_5dp
        )
        typeArray.recycle()
    }
}