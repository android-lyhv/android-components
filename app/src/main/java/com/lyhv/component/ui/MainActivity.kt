package com.lyhv.component.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.lyhv.component.R
import com.lyhv.component.common.HasSupportFragmentInjectorActivity
import com.lyhv.component.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : HasSupportFragmentInjectorActivity() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val mainViewModel: MainViewModel by viewModels { factory }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}