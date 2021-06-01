package com.lyhv.component.common.base

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    private var disableView: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun disableView(disable: Boolean) {
        this.disableView = disable
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return if (disableView) true
        else super.dispatchTouchEvent(ev)
    }
}