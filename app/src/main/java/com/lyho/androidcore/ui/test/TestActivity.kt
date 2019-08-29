package com.lyho.androidcore.ui.test

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lyho.androidcore.R
import com.lyho.androidcore.model.entities.User
import com.lyho.androidcore.model.repository.UserRepositoryTest

@SuppressLint("Registered")
class TestActivity : AppCompatActivity(), TestActivityViewListener {
    override fun onNewUser(user: User?) {
        // TODO update ui here
    }

    private val mTestViewModel by lazy {
        TestCoroutineViewModel.newInstance(application, UserRepositoryTest(application))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Observer data
//        mTestViewModel.getUser(0)
//        mTestViewModel.mTestLiveData.observe(this, Observer {
//            onNewUser(it)
//        })
//        mTestViewModel.getUserSuppned()
//        mTestViewModel.getUserSuppnedChain()
//        mTestViewModel.runCoroutineScope()
//        mTestViewModel.runSupperVisoCoroutineScope()
//        mTestViewModel.childJobDefine()
        mTestViewModel.asynchronousFlow()
    }
}
