package com.lyho.androidbase.ui.test

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lyho.androidbase.R
import com.lyho.androidbase.model.entities.User
import com.lyho.androidbase.model.repository.UserRepository

@SuppressLint("Registered")
class TestActivity : AppCompatActivity(), TestActivityViewListener {
    override fun onNewUser(user: User?) {
        // TODO update ui here
    }

    private lateinit var mTestViewModel: TestViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Create View Model
        mTestViewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return TestViewModel(this@TestActivity.application, UserRepository(this@TestActivity.application)) as T
            }
        }).get(TestViewModel::class.java)
        // Observer data
        mTestViewModel.getUser(0)
        mTestViewModel.mTestLiveData.observe(this, Observer {
            onNewUser(it)
        })
    }
}
