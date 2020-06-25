package com.lyhv.component.corountine

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.lyhv.component.R
import com.lyhv.component.common.BaseActivity
import kotlinx.android.synthetic.main.activity_main.btn_cancel

@SuppressLint("Registered")
class CoroutineActivity : BaseActivity() {

    private val coroutineViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(CoroutineViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        btn_cancel.setOnClickListener {
            coroutineViewModel.testCancellation()
        }
    }
}
