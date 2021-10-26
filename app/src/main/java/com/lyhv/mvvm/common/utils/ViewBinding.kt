package com.lyhv.mvvm.common.utils

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods

@BindingMethods(
    BindingMethod(type = View::class, attribute = "enabled", method = "setEnabled")
)
object ViewBinding

@BindingAdapter("visibilityVisibleIf")
fun View.setVisibilityVisibleIf(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

@BindingAdapter("visibilityGoneIf")
fun View.setVisibilityGoneIf(condition: Boolean) {
    visibility = if (condition) View.GONE else View.VISIBLE
}

@BindingAdapter("visibilityInvisibleIf")
fun View.setVisibilityInvisibleIf(condition: Boolean) {
    visibility = if (condition) View.INVISIBLE else View.VISIBLE
}

@BindingAdapter("visibilityGoneIfNullOrEmpty")
fun View.setVisibilityGoneIfNullOrEmpty(text: CharSequence?) {
    visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
}