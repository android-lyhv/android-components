package com.lyhv.component.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.lyhv.component.R
import com.lyhv.component.common.HasSupportFragmentInjectorActivity
import com.lyhv.component.databinding.ActivityMainBinding

class MainActivity : HasSupportFragmentInjectorActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}