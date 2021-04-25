package com.app.roomwithkotlincoroutine.db

import com.app.roomwithkotlincoroutine.db.entity.Demand
import com.app.roomwithkotlincoroutine.db.entity.DemandTransaction
import com.app.roomwithkotlincoroutine.db.entity.Discount
import com.app.roomwithkotlincoroutine.db.entity.Product
import com.app.roomwithkotlincoroutine.db.pojo.DemandTransactionAndProductMultiSelect
import com.app.roomwithkotlincoroutine.db.pojo.DemandWithProduct
import com.app.roomwithkotlincoroutine.db.pojo.ProductWithCoupon

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getUsers(): List<Product> = appDatabase.productDao().getAll()

    override suspend fun insertAll(users: List<Product>) = appDatabase.productDao().insertAll(users)

    override suspend fun insertTransaction(users: DemandTransaction) =
        appDatabase.demandTransactionDao().insert(users)

    override suspend fun getLastInsertedDiscount() =
        appDatabase.discountDao().getLastInsertedValue()

    override suspend fun insert(users: Product) = appDatabase.productDao().insert(users)

    override suspend fun insertDemand(users: Demand) = appDatabase.demandDao().insert(users)

    override suspend fun updateDemand(users: Demand) = appDatabase.demandDao().update(users)

    override suspend fun insertDiscount(users: Discount): Long =
        appDatabase.discountDao().insert(users)

    override suspend fun getProductWithDiscount(): List<ProductWithCoupon> =
        appDatabase.productDao().getProductWithDiscount()

    override suspend fun getAllDemands(): List<DemandWithProduct> =
        appDatabase.demandDao().getAllDemands()

    override suspend fun getDemandList(): List<DemandWithProduct> =
        appDatabase.demandDao().getAllDemands()

    override suspend fun findAllByDemandId(demandId: Int): List<DemandTransactionAndProductMultiSelect> =
        appDatabase.demandTransactionDao().findAllByDemandId(demandId)

   override suspend fun deleteAllExisting(demandId: Int) =
        appDatabase.demandTransactionDao().deleteAllExisting(demandId)

}