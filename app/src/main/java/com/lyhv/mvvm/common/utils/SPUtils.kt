package com.lyhv.mvvm.common.utils

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi

class SPUtils(context: Context, private val moshi: Moshi) {

    private val sp: SharedPreferences =
        context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val SHARED_NAME = "era-member-android"
    }
}
