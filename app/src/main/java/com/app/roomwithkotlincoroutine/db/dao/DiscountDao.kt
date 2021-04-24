package com.app.roomwithkotlincoroutine.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.app.roomwithkotlincoroutine.db.entity.Discount

@Dao
interface DiscountDao {

    @Query("SELECT * FROM discount")
    suspend fun getAll(): List<Discount>

    @Query("SELECT * FROM discount ORDER BY id DESC LIMIT 1")
    suspend fun getLastInsertedValue(): Discount

    @Insert
    suspend fun insertAll(users: List<Discount>)

    @Insert
    suspend fun insert(users: Discount) : Long

    @Delete
    suspend fun delete(user: Discount)
}