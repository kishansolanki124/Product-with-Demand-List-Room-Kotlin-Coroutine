package com.app.roomwithkotlincoroutine.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.app.roomwithkotlincoroutine.db.entity.DemandTransaction
import com.app.roomwithkotlincoroutine.db.pojo.DemandTransactionAndProductMultiSelect

@Dao
interface DemandTransactionDao {

    @Query("SELECT * FROM demandtransaction")
    suspend fun getAll(): List<DemandTransaction>

    @Query("SELECT * FROM demandtransaction LEFT join product ON demandtransaction.productId = product.id " +
            "LEFT join discount ON discount.id = product.discountId WHERE demandId = :demandId")
    suspend fun findAllByDemandId(demandId : Int): List<DemandTransactionAndProductMultiSelect>

    @Query("SELECT * FROM demandtransaction ORDER BY id DESC LIMIT 1")
    suspend fun getLastInsertedValue(): DemandTransaction

    @Query(" DELETE FROM demandtransaction WHERE demandId=:demandId ")
    suspend fun deleteAllExisting(demandId: Int)

    @Insert
    suspend fun insertAll(users: List<DemandTransaction>)

    @Insert
    suspend fun insert(users: DemandTransaction)

    @Delete
    suspend fun delete(user: DemandTransaction)
}