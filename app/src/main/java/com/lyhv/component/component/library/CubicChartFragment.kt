package com.lyhv.component.component.library

import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lyhv.component.databinding.FragmentCubicChartBinding
import java.util.ArrayList
import kotlin.random.Random

class CubicChartFragment : Fragment() {
    private lateinit var binding: FragmentCubicChartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCubicChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        update()
        binding.cubicChart.setOnClickListener {
            update()
        }
    }

    private fun update() {
        val yVals =
            ArrayList<PointF>()
        for (i in 0 until 10 step 1) {
            val mult: Float = 30f + 1
            val `val` =
                (Math.random() * mult).toFloat() + 10 // + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(PointF(i.toFloat(), Random.nextInt(3).toFloat()))
        }
        binding.cubicChart.updateGraph(yVals, false)
    }
}
