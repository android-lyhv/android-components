package com.era.member.utils

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.widget.NestedScrollView

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun EditText.onEditorActionNext(onEditorActionNext: () -> Unit) {
    this.setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            onEditorActionNext.invoke()
        }
        return@setOnEditorActionListener true
    }
}

fun EditText.onEditorActionDone(debounceTime: Long = 1000L, onEditorActionDone: () -> Unit) {
    this.setOnEditorActionListener(object : TextView.OnEditorActionListener {
        var lastClickTime: Long = 0
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) {
                    lastClickTime = SystemClock.elapsedRealtime()
                    return true
                }
                onEditorActionDone.invoke()
                lastClickTime = SystemClock.elapsedRealtime()
            }
            return true
        }
    })
}

fun EditText.togglePasswordVisibility() {
    if (isShowingPasswordText()) {
        transformationMethod = PasswordTransformationMethod()
        setSelection(length())
    } else {
        transformationMethod = HideReturnsTransformationMethod()
        setSelection(length())
    }
}

fun EditText.isShowingPasswordText(): Boolean {
    return transformationMethod is HideReturnsTransformationMethod
}

fun ViewGroup.viewsRecursive(): List<View> {
    val views = this.children.toList()
    return views.flatMap {
        when (it) {
            is ViewGroup -> {
                val listViews: MutableList<View> = it.viewsRecursive().toMutableList()
                listViews.add(it as View)
                listViews
            }

            else -> listOf(it)
        }
    }
}

fun View.click(debounceTime: Long = 1000L, action: (view: View) -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) {
                lastClickTime = SystemClock.elapsedRealtime()
                return
            }
            action(v)
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun View.applyBackgroundColorBasedOnMode(modeColor: Int) {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(modeColor, typedValue, true)
    val color = ContextCompat.getColor(context, typedValue.resourceId)
    setBackgroundColor(color)
}

fun TextView.applyTextColorBasedOnMode(modeColor: Int) {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(modeColor, typedValue, true)
    val color = ContextCompat.getColor(context, typedValue.resourceId)
    setTextColor(color)
}

fun ImageView.applyDrawableBasedOnMode(modeDrawable: Int) {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(modeDrawable, typedValue, true)
    setImageResource(typedValue.resourceId)
}

fun View.getTextColorBasedOnMode(modeColor: Int): Int {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(modeColor, typedValue, true)
    return typedValue.resourceId
}

fun View.getCompatColorBasedOnMode(modeColor: Int): Int {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(modeColor, typedValue, true)
    return ContextCompat.getColor(context, typedValue.resourceId)
}

fun getTextColorBasedOnMode(context: Context, modeColor: Int): Int {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(modeColor, typedValue, true)
    return typedValue.resourceId
}

fun View.margin(
    left: Int? = null,
    top: Int? = null,
    right: Int? = null,
    bottom: Int? = null
) {
    layoutParams<ViewGroup.MarginLayoutParams> {
        left?.run { leftMargin = 0 }
        top?.run { topMargin = this }
        right?.run { rightMargin = 0 }
        bottom?.run { bottomMargin = 0 }
    }
}

inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
    if (layoutParams is T) block(layoutParams as T)
}

fun NestedScrollView.scrollToBottom() {
    val lastChild = getChildAt(childCount - 1)
    val bottom = lastChild.bottom + paddingBottom
    val delta = bottom - (scrollY + height)
    smoothScrollBy(0, delta)
}

fun ScrollView.scrollToBottom() {
    val lastChild = getChildAt(childCount - 1)
    val bottom = lastChild.bottom + paddingBottom
    val delta = bottom - (scrollY + height)
    smoothScrollBy(0, delta)
}

inline fun getValueAnimator(
    duration: Long,
    interpolator: TimeInterpolator,
    fromInt: Int,
    toInt: Int,
    crossinline updateListener: (progress: Int, dental: Int) -> Unit
): ValueAnimator {
    var lastDistance = fromInt
    val a = ValueAnimator.ofInt(fromInt, toInt)
    a.addUpdateListener {
        val value = it.animatedValue as Int
        val dental = value - lastDistance
        lastDistance = value
        if (dental != 0) {
            updateListener(value, dental)
        }
    }
    a.duration = duration
    a.interpolator = interpolator
    return a
}
