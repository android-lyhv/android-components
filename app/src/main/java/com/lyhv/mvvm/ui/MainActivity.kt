package com.lyhv.mvvm.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.lyhv.mvvm.R
import com.lyhv.mvvm.common.HasSupportFragmentInjectorActivity
import com.lyhv.mvvm.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : HasSupportFragmentInjectorActivity() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}