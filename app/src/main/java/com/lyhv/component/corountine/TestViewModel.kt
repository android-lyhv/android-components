package com.lyhv.component.corountine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyhv.component.model.entities.User
import com.lyhv.component.common.helper.LogHelper
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Ly Ho V. on February 20, 2019
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
class TestViewModel :
    ViewModel() {
    val mTestLiveData: MutableLiveData<User> = MutableLiveData()
    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        LogHelper.logCouroutines("aaaa ${throwable.message}")
    }
    val coroutinContext = Job() + handler + Dispatchers.Main
    val scope = CoroutineScope(coroutinContext)
    fun testCancellation() {
        viewModelScope.launch {
            scope.launch {
                try {
                    delay(1000)
                } catch (e: CancellationException) {
                    LogHelper.logCouroutines("Job Cancel ${e.message}")
                }
            }
            delay(500)
            scope.cancel()
        }
    }
}