package com.lyhv.component.extension.motionlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lyhv.component.R
import kotlinx.android.synthetic.main.montion_layout_sample.motionLayout

internal class MotionLayoutSample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.montion_layout_sample)
        motionLayout.transitionToEnd()
    }
}