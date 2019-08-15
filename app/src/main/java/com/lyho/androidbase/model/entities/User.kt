package com.lyho.androidbase.model.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/**
 * Created by Ly Ho V. on April 17, 2018
 */
@Entity(tableName = "user_table")
class User {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id = 0
}