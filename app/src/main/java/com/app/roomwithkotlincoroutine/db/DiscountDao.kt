package com.app.roomwithkotlincoroutine.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DiscountDao {

    @Query("SELECT * FROM discount")
    suspend fun getAll(): List<Discount>

    @Insert
    suspend fun insertAll(users: List<Discount>)

    @Insert
    suspend fun insert(users: Discount)

    @Delete
    suspend fun delete(user: Discount)
}