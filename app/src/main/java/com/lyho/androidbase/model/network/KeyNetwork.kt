package com.lyho.androidbase.model.network

/**
 * Created by lyhv on August 19, 2019
 * Copyright @ est-rouge. All rights reserved
 */
internal abstract class KeyNetwork {
    private val mapKeys: HashMap<String, String> = HashMap()
    fun setValue(key: String, value: String) {
        mapKeys[key] = value
    }

    fun getValue(key: String): String {
        return mapKeys.getValue(key)
    }
}
