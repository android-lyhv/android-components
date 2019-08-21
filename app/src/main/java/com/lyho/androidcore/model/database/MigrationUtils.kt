package com.lyho.androidcore.model.database

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration

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
