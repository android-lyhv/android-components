package com.lyho.androidbase.model.local

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration

/**
 * Created by Ly Ho V. on November 22, 2018
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
object MigrationUtils {
    fun getMigration(oldVersion: Int, newVersion: Int): Migration {
        return object : Migration(oldVersion, newVersion) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // TODO handle migration here
            }
        }
    }
}
