package com.lyhv.mvvm.common.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateTimeUtils {
    enum class DatePattern constructor(val value: String) {
        COMMON("yyyy-MM-dd HH:mm:ss"),
    }

    val COMMON_DATE_FORMAT = SimpleDateFormat(DatePattern.COMMON.value, Locale.JAPANESE)
}

fun Date.inRangeDate(startDate: Date, endDate: Date): Boolean {
    return this == startDate || this == endDate || (this.before(endDate) && this.after(startDate))
}

fun Date.afterRangeDate(startDate: Date, endDate: Date): Boolean {
    return this.after(endDate) && (startDate.before(endDate) || startDate == endDate)
}

fun Date.beforeRangeDate(startDate: Date, endDate: Date): Boolean {
    return this.before(startDate) && (startDate.before(endDate) || startDate == endDate)
}
