package com.app.roomwithkotlincoroutine.db.dao

import androidx.room.*
import com.app.roomwithkotlincoroutine.db.entity.Demand
import com.app.roomwithkotlincoroutine.db.pojo.DemandWithProduct

@Dao
interface DemandDao {

    @Query("SELECT * FROM demand")
    suspend fun getAll(): List<Demand>

    @Query("SELECT Demand.*, COUNT(DemandTransaction.id) as total FROM demand left join DemandTransaction on demand.id = DemandTransaction.demandId GROUP BY demand.id")
    suspend fun getAllDemands(): List<DemandWithProduct>

    @Query("SELECT * FROM demand ORDER BY id DESC LIMIT 1")
    suspend fun getLastInsertedValue(): Demand

    @Insert
    suspend fun insertAll(users: List<Demand>)

    @Insert
    suspend fun insert(users: Demand): Long

    @Update
    suspend fun update(users: Demand)

    @Delete
    suspend fun delete(user: Demand)
}