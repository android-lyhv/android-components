package com.lyhv.component.component.corountine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyhv.component.common.logger
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Ly Ho V. on February 20, 2019
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
class CoroutineViewModel :
    ViewModel() {
    val logger = logger<CoroutineViewModel>()

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        logger.debug("exceptionHandler ${throwable.message}")
    }
    val coroutinContext = SupervisorJob() + exceptionHandler + Dispatchers.Main
    val otherScope = CoroutineScope(coroutinContext)

    /**
     * Test cancel courtine
     */
    fun testCancellation() {
        logger.debug("start")
        viewModelScope.launch {
            /**
             * Not a child job
             */
            otherScope.launch {
                try {
                    delay(1000)
                    logger.debug("Finish job 1")
                } catch (e: CancellationException) {
                    logger.debug("Job 1 ${e.message}")
                }
            }
            /**
             * Not a child job
             */
            viewModelScope.launch {
                try {
                    delay(1000)
                    logger.debug("Finish job 2")
                } catch (e: CancellationException) {
                    logger.debug("Job 2 ${e.message}")
                }
            }
            /**
             * Child job
             */
            launch {
                try {
                    delay(1000)
                    logger.debug("Finish job 3")
                } catch (e: CancellationException) {
                    logger.debug("Job 3 ${e.message}")
                }
            }

        }
        viewModelScope.launch {
            delay(500)
            viewModelScope.cancel()
        }
    }
}