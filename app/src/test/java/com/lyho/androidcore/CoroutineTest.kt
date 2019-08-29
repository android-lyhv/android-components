package com.lyho.androidcore

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CoroutineTest {
    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    @Test
    fun runTestSample() {
        MainScope().launch {
            print("aaaa")
        }
        mainCoroutineRule.pauseDispatcher()
        mainCoroutineRule.runBlockingTest {

        }
    }
}