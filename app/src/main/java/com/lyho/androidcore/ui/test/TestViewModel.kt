package com.lyho.androidcore.ui.test

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lyho.androidcore.model.entities.Result
import com.lyho.androidcore.model.entities.User
import com.lyho.androidcore.model.network.ApiError
import com.lyho.androidcore.model.network.ResultCallBack
import com.lyho.androidcore.model.repository.IUserRepository
import com.lyho.androidcore.ui.common.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

/**
 * Created by Ly Ho V. on February 20, 2019
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
class TestViewModel(application: Application, private val userRepository: IUserRepository) :
    BaseAndroidViewModel(application) {
    val mTestLiveData: MutableLiveData<User> = MutableLiveData()
    fun getUser(userId: Int) {
        userRepository.getUserAsync(userId, object : ResultCallBack<User>() {
            override fun success(t: User?) {
                mTestLiveData.postValue(t)
            }

            override fun failure(apiError: ApiError?) {
                mErrorLiveData.postValue(apiError)
            }
        })
    }

    fun getUserSuppned() {
        viewModelScope.launch {
            Log.d("aaa", "start")
            val test = async { userRepository.getUser(1) }
            val test1 = async { userRepository.getUser(2) }
            Log.d("aaa", test.await().toString())
            Log.d("aaa", test1.await().toString())
            val test2 = userRepository.getUser(3)
            Log.d("aaa", test2.toString())
            val test3 = userRepository.getUser(4)
            Log.d("aaa", test3.toString())
        }
    }

    fun getUserSuppnedChain() {
        val channel = Channel<Result<User>>()
        viewModelScope.launch {
            Log.d("aaaa", "start chain")
            for (i in 0 until 2) {
                launch {
                    channel.send(userRepository.getUser(i))
                }
            }
            repeat(2) {
                val x = channel.receive()
                Log.d("aaa chain", x.toString())
            }

        }

    }
}