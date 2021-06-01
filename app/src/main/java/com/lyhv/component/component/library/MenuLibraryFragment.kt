package com.lyhv.component.component.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.era.member.utils.click
import com.lyhv.component.R
import com.lyhv.component.databinding.MenuLibraryFragmentBinding

class MenuLibraryFragment : Fragment() {
    private lateinit var binding: MenuLibraryFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MenuLibraryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCubicChart.click {
            findNavController().navigate(R.id.cubicChartFragment)
        }
    }
}