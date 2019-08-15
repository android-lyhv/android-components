package com.lyho.androidbase.model.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.lyho.androidbase.model.entities.User

@Database(
    entities = [User::class],
    version = DatabaseConfig.DB_SCHEMA_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dataDao(): DatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        internal fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            DatabaseConfig.DB_NAME
                        )
                            .addMigrations(MigrationUtils.getMigration(1, 2))
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}