package com.lyho.androidcore.ui.motionlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lyho.androidcore.R
import kotlinx.android.synthetic.main.montion_layout_sample.motionLayout

internal class MotionLayoutSample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.montion_layout_sample)
        motionLayout.transitionToEnd()
    }
}