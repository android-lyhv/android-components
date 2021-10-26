package com.lyhv.mvvm.common.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun Fragment.getLogger(): Logger {
    return LoggerFactory.getLogger(this::class.java)
}

fun Activity.getLogger(): Logger {
    return LoggerFactory.getLogger(this::class.java)
}

fun ViewModel.getLogger(): Logger {
    return LoggerFactory.getLogger(this::class.java)
}

inline fun <reified T> logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}