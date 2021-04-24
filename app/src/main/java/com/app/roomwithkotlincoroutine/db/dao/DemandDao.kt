package com.app.roomwithkotlincoroutine.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.app.roomwithkotlincoroutine.db.entity.Demand
import com.app.roomwithkotlincoroutine.db.entity.Discount

@Dao
interface DemandDao {

    @Query("SELECT * FROM demand")
    suspend fun getAll(): List<Demand>

    @Query("SELECT * FROM demand ORDER BY id DESC LIMIT 1")
    suspend fun getLastInsertedValue(): Demand

    @Insert
    suspend fun insertAll(users: List<Demand>)

    @Insert
    suspend fun insert(users: Demand) : Long

    @Delete
    suspend fun delete(user: Demand)
}