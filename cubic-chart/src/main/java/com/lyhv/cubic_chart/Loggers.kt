package com.lyhv.cubic_chart

import androidx.fragment.app.Fragment
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun Fragment.getLogger(): Logger {
    return LoggerFactory.getLogger(this::class.java)
}

inline fun <reified T> logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}