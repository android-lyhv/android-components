package com.lyho.androidcore.model.database

import androidx.room.Dao
import androidx.room.Query
import com.lyho.androidcore.model.entities.User

/**
 * Created by Ly Ho V. on November 22, 2018
 * Copyright © 2017 Ly Ho V. All rights reserved.
 */
@Dao
interface DatabaseDao {
    @Query("SELECT * from user_table where id = :userId")
    fun getUsers(userId: Int): User
}
