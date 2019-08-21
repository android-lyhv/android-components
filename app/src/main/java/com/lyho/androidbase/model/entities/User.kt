package com.lyho.androidbase.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.NonNull

/**
 * Created by Ly Ho V. on April 17, 2018
 */
@Entity(tableName = "user_table")
class User {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id = 0
    @ColumnInfo(name = "name")
    var name = ""
    @ColumnInfo(name = "age")
    var age = 0
}