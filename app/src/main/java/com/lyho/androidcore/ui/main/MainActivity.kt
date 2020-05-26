package com.lyho.androidcore.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lyho.androidcore.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cubic_chart.setOnClickListener {
            startActivity(Intent(this, CubicChartActivity::class.java))
        }
    }
}
