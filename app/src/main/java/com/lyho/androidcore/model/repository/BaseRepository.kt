package com.lyho.androidcore.model.repository

import android.app.Application
import com.lyho.androidcore.model.database.AppDatabase
import com.lyho.androidcore.model.database.DatabaseDao
import com.lyho.androidcore.model.helper.AppExecutors
import com.lyho.androidcore.model.network.ApiClient
import com.lyho.androidcore.model.network.ApiService
import com.lyho.androidcore.model.network.NetworkConfig

/**
 * Created by Ly Ho V. on November 22, 2018
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
abstract class BaseRepository constructor(application: Application) {
    var mDataDao: DatabaseDao? = null
        private set
    var mAppExecutors: AppExecutors
        private set
    protected var apiService: ApiService
        private set

    init {
        mDataDao = AppDatabase.getDatabase(application)?.dataDao()
        mAppExecutors = AppExecutors()
        apiService = ApiClient.newInstance(NetworkConfig()).createService(ApiService::class.java)
    }
}
