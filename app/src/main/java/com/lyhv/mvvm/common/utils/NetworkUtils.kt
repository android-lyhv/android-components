package com.lyhv.mvvm.common.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Ly Ho V. on April 18, 2018
 */
object NetworkUtils {
    fun isNetworkConnected(context: Context): Boolean {
        return try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
        } catch (e: Exception) {
            false
        }
    }
}
