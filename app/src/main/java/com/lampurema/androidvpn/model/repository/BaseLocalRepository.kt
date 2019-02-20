package com.lampurema.androidvpn.model.repository

import android.app.Application
import com.lampurema.androidvpn.model.database.DatabaseDao
import com.lampurema.androidvpn.model.helper.AppExecutors
import com.lampurema.androidvpn.model.database.AppDatabase

/**
 * Created by Ly Ho V. on November 22, 2018
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
abstract class BaseLocalRepository constructor(application: Application) {
    protected var dataDao: DatabaseDao? = null
    protected var appExecutors: AppExecutors

    init {
        dataDao = AppDatabase.getDatabase(application)?.dataDao()
        appExecutors = AppExecutors()
    }
}
