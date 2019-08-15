package com.lyho.androidbase.model.network

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

/**
 * @author Ly Ho V.
 */
class ApiKeyStore private constructor() {
    fun saveKey(key: String, value: String) {
        sSharedPreferences!!.edit().putString(key, value).apply()
    }

    operator fun get(key: String): String? {
        return sSharedPreferences?.getString(key, "")
    }

    fun clearKey() {
        sSharedPreferences?.edit()?.clear()?.apply()
    }

    companion object {
        const val ACCESS_TOKEN_KEY = "access_token"
        private const val API_KEY_PREFERENCES = "API_KEY_PREFERENCES"
        private var sSessionStore: ApiKeyStore? = null
        private var sSharedPreferences: SharedPreferences? = null

        @Synchronized
        fun getInstance(application: Application): ApiKeyStore? {
            sSharedPreferences = application.getSharedPreferences(API_KEY_PREFERENCES, Context.MODE_PRIVATE)
            if (sSessionStore == null) {
                sSessionStore = ApiKeyStore()
            }
            return sSessionStore
        }
    }
}
