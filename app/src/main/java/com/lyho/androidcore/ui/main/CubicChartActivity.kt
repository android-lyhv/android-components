package com.lyho.androidcore.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.Entry
import com.lyho.androidcore.R
import kotlinx.android.synthetic.main.acitivity_cubic_chart.btn_change
import kotlinx.android.synthetic.main.acitivity_cubic_chart.cubic_chart
import java.util.ArrayList

class CubicChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_cubic_chart)
        update()
        btn_change.setOnClickListener {
            update()
        }
    }

    fun update() {
        val yVals =
            ArrayList<Entry>()
        for (i in 0 until 10 step 1) {
            val mult: Float = 30f + 1
            val `val` =
                (Math.random() * mult).toFloat() + 10 // + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(Entry(i.toFloat(), kotlin.random.Random.nextInt(3).toFloat()))
        }
        cubic_chart.updateGraph(yVals, false)
    }
}
