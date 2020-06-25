package com.lyhv.component.model.network

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

/**
 * @author Ly Ho V.
 */
class ApiKeyStore private constructor() {
    fun saveKey(key: String, value: String) {
        sSharedPreferences.edit().putString(key, value).apply()
    }

    operator fun get(key: String): String {
        return sSharedPreferences.getString(key, "") ?: ""
    }

    fun clearAllKeys() {
        sSharedPreferences.edit().clear().apply()
    }

    companion object {
        private const val API_KEY_PREFERENCES = "API_KEY_PREFERENCES"
        const val ACCESS_TOKEN_KEY = "access_token"
        const val API_KEY = "api_key"
        private lateinit var sSessionStore: ApiKeyStore
        private lateinit var sSharedPreferences: SharedPreferences

        @Synchronized
        fun getInstance(application: Application): ApiKeyStore {
            sSharedPreferences = application.getSharedPreferences(API_KEY_PREFERENCES, Context.MODE_PRIVATE)
            if (sSessionStore == null) {
                sSessionStore = ApiKeyStore()
            }
            return sSessionStore
        }
    }
}
