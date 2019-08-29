package com.lyho.androidcore.ui.test

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lyho.androidcore.model.entities.Result
import com.lyho.androidcore.model.entities.User
import com.lyho.androidcore.model.network.ApiError
import com.lyho.androidcore.model.network.ResultCallBack
import com.lyho.androidcore.model.repository.IUserRepository
import com.lyho.androidcore.ui.common.helper.LogHelper
import com.lyho.androidcore.ui.common.viewmodel.BaseAndroidViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * Created by Ly Ho V. on February 20, 2019
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
class TestCoroutineViewModel(
    application: Application
) :
    BaseAndroidViewModel(application) {
    val mTestLiveData: MutableLiveData<User> = MutableLiveData()
    private lateinit var userRepository: IUserRepository
    /**
     *  Handler
     */
    private val handlerException = CoroutineExceptionHandler { context, exception ->
        LogHelper.logCouroutine("Exception  ${exception.message}")
    }

    class myCancaleExaption : java.util.concurrent.CancellationException()

    private val cancellationException = CancellationException()
    val mainJobContext = Job() + Dispatchers.Main + handlerException

    // job cancel child -> cancel parent
    // SupervisorJob cancel child -> parent not cancel
    val manSupervisorJob = SupervisorJob() + Dispatchers.Main + handlerException
    val mainScope = CoroutineScope(mainJobContext)
    val mainSubperScope = CoroutineScope(manSupervisorJob)

    companion object {
        fun newInstance(
            activity: Application,
            userRepository: IUserRepository
        ): TestCoroutineViewModel {
            val coroutineViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(activity)
                .create(TestCoroutineViewModel::class.java)
            coroutineViewModel.userRepository = userRepository
            return coroutineViewModel
        }
    }

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
        viewModelScope.launch(Dispatchers.IO) {
            LogHelper.logCouroutine(Thread.currentThread().name)
            val test = async { userRepository.getUser(1) }
            val test1 = async { userRepository.getUser(2) }
            Log.d("aaa", test.await().toString())
            Log.d("aaa", test1.await().toString())
            val test2 = userRepository.getUser(3)
            Log.d("aaa", test2.toString())
            val test3 = userRepository.getUser(4)
            Log.d("aaa", test3.toString())
        }
        LogHelper.logCouroutine(Thread.currentThread().name)
    }

    fun getUserSuppnedChain() {
        val channel = Channel<Result<User>>()
        viewModelScope.launch {
            LogHelper.logCouroutine(System.getProperties().toString())
            LogHelper.logCouroutine(Thread.currentThread().name)
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

        Thread().run {
            LogHelper.logCouroutine(Thread.currentThread().toString())
        }
    }

    fun childJobDefine() {
        // launch a coroutine to process some kind of incoming request
        viewModelScope.launch {
            val request = viewModelScope.launch {
                // it spawns two other jobs, one with GlobalScope
                GlobalScope.launch {
                    println("job1: I run in GlobalScope and execute independently!")
                    delay(1000)
                    println("job1: I am not affected by cancellation of the request")
                }
                // and the other inherits the parent context
                launch {
                    delay(100)
                    println("job2: I am a child of the request coroutine")
                    delay(1000)
                    println("job2: I will not execute this line if my parent request is cancelled")
                }
            }
            delay(500)
            request.cancel() // cancel processing of the request
            delay(1000) // delay a second to see what happens
            println("main: Who has survived request cancellation?")
        }
    }

    /**
     * For testing job
     */
    fun runCoroutineScope() {
        viewModelScope.launch {
            val mainjob = mainScope.launch(Dispatchers.Default) {
                val childJob1 = launch {
                    repeat(5) {
                        delay(1000)
                        LogHelper.logCouroutine("Job1 $it")
                        throw Exception("Exception Job")
                    }
                }

            }
         val tes =   withContext(Dispatchers.IO){
                val a = 0
            }

            val childJob2 = mainScope.launch {
                repeat(5) {
                    delay(500)
                    LogHelper.logCouroutine("Job2 $it")
                }
            }
            // will call all job
            delay(1000)
            // wait all job completed
            mainjob.join()
            LogHelper.logCouroutine("mainjob is cancel")
            mainjob.cancel()
            LogHelper.logCouroutine("mainjob is Done")

        }
    }

    /**
     * For testing supervisol job
     */
    fun runSupperVisoCoroutineScope() {
        viewModelScope.launch {
            val mainjob = mainSubperScope.launch {
                val childJob1 = launch {
                    repeat(5) {
                        delay(1000)
                        LogHelper.logCouroutine("Job1 Subper $it")
                        throw Exception("aaaa Subper")
                    }
                }

            }

            val childJob2 = mainSubperScope.launch {
                repeat(5) {
                    delay(500)
                    LogHelper.logCouroutine("Job2 Subper$it")
                }
            }
            // will call all job
            delay(1000)
            // wait all job completed
            mainjob.join()
            LogHelper.logCouroutine("mainjob  Subper is cancel")
            mainjob.cancel(cancellationException)
            LogHelper.logCouroutine("mainjob Subper is Done")
        }
    }

    /**
     * For stream data flow
     */

    fun foo(): Flow<Int> = flow {
        // flow builder
        for (i in 1..3) {
            delay(1000) // pretend we are doing something useful here
            emit(i) // emit next value
        }
    }

    fun asynchronousFlow() {

        runBlocking {
            // Launch a concurrent coroutine to check if the main thread is blocked
//            launch {
//                for (k in 1..3) {
//                    println("I'm not blocked $k")
//                    delay(100)
//                }
//            }
            // Collect the flow
            //   foo().collect { value -> println(value) }
        }
        viewModelScope.launch {
            println("Calling foo...")
            val flow = foo()
            println("Calling collect...")
            flow.collect { value -> println(value) }
            println("Calling collect again...")
            flow.collect { value -> println(value) }
        }
    }
}