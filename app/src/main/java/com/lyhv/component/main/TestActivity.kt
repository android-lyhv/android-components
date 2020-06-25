package com.lyhv.component.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.lyhv.component.R
import com.lyhv.component.common.BaseActivity
import com.lyhv.component.corountine.TestViewModel
import kotlinx.coroutines.launch

@SuppressLint("Registered")
class TestActivity : BaseActivity() {

    private lateinit var mTestViewModel: TestViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Create View Model
        mTestViewModel = ViewModelProvider.NewInstanceFactory().create(TestViewModel::class.java)
        // Observer data
//        mTestViewModel.getUser(0)
//        mTestViewModel.mTestLiveData.observe(this, Observer {
//            onNewUser(it)
//        })
//        mTestViewModel.getUserSuppned()
//        mTestViewModel.getUserSuppnedChain()
        mTestViewModel.testCancellation()
        lifecycleScope.launch {
            // Log.d("aaa", test)
        }
    }
}
