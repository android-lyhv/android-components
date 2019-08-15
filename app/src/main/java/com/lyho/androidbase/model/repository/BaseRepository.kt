package com.lyho.androidbase.model.repository

import android.app.Application
import com.lyho.androidbase.model.helper.AppExecutors
import com.lyho.androidbase.model.local.AppDatabase
import com.lyho.androidbase.model.local.DatabaseDao
import com.lyho.androidbase.model.remote.ApiClient
import com.lyho.androidbase.model.remote.ApiService

/**
 * Created by Ly Ho V. on November 22, 2018
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
abstract class BaseRepository constructor(application: Application) {
    var mDataDao: DatabaseDao? = null
        private set
    var mAppExecutors: AppExecutors
        private set
    var mApiService: ApiService? = null
        private set

    init {
        mDataDao = AppDatabase.getDatabase(application)?.dataDao()
        mAppExecutors = AppExecutors()
        mApiService = ApiClient.getInstance()?.apiService
    }
}
