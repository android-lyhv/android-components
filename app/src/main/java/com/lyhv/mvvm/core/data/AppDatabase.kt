package com.lyhv.mvvm.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.squareup.moshi.Moshi

/**
 * Database schema used by the App
 */
@Database(
    entities = [
        RoomEntryDemo::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        lateinit var moshi: Moshi
        const val DB_NAME = "android_component.db"
        const val DB_VERSION = 1
    }
}