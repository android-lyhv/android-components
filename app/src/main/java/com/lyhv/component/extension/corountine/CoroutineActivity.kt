package com.lyhv.component.extension.corountine

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.lyhv.component.R

@SuppressLint("Registered")
class CoroutineActivity : Activity() {

    private val coroutineViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(CoroutineViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {

    }
}
