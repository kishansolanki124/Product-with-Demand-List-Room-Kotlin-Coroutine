package com.app.roomwithkotlincoroutine.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.app.roomwithkotlincoroutine.db.entity.DemandTransaction

@Dao
interface DemandTransactionDao {

    @Query("SELECT * FROM demandtransaction")
    suspend fun getAll(): List<DemandTransaction>

    @Query("SELECT * FROM demandtransaction ORDER BY id DESC LIMIT 1")
    suspend fun getLastInsertedValue(): DemandTransaction

    @Insert
    suspend fun insertAll(users: List<DemandTransaction>)

    @Insert
    suspend fun insert(users: DemandTransaction)

    @Delete
    suspend fun delete(user: DemandTransaction)
}