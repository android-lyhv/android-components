package com.lyhv.mvvm.common.utils

import java.util.concurrent.TimeUnit

/**
 * Created by Ly Ho V. on October 08, 2018
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
object TimeUtils {
    /**
     * 150 seconds to "02:30"
     */
    fun getTileDurationFromSeconds(seconds: Long): String {
        val hours = TimeUnit.SECONDS.toHours(seconds)
        val minutes = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.HOURS.toMinutes(hours)
        val seconds = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toMinutes(seconds) * 60
        return StringBuilder()
                .append(if (hours == 0.toLong()) "" else if (hours < 10) "0$hours" else hours)
                .append(if (hours <= 0) "" else ":")
                .append(if (minutes < 10) "0$minutes" else minutes)
                .append(":")
                .append(if (seconds < 10) "0$seconds" else seconds)
                .toString()
    }
}
