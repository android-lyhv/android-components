package com.lyho.androidbase.model.network

/**
 * Created by lyhv on August 19, 2019
 * Copyright @ est-rouge. All rights reserved
 */
class KeyNetwork(keyName: String) : BaseKeyNetwork(keyName) {
    override fun getValue(): String {
        if (keyName.isBlank()) {
            throw Exception("Key name can't blank")
        }
        return ApiKeyStore.getInstance(ApplicationConfig.getApplication())[keyName]
    }
}
