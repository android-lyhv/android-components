package com.lyhv.mvvm.core.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "stamp_rally")
class RoomEntryDemo {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:Json(name = "id")
    var id: String = ""
}