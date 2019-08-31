package com.lyho.androidcore.ui.test

import android.annotation.SuppressLint
import android.os.Bundle
import com.lyho.androidcore.R
import com.lyho.androidcore.model.entities.User
import com.lyho.androidcore.ui.common.BaseActivity

@SuppressLint("Registered")
class TestActivityDialog : BaseActivity(), TestActivityViewListener {
    override fun onNewUser(user: User?) {
        // TODO update ui here
    }

    private lateinit var mTestViewModel: TestViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
