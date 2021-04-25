package com.app.roomwithkotlincoroutine.db

import com.app.roomwithkotlincoroutine.db.entity.Demand
import com.app.roomwithkotlincoroutine.db.entity.DemandTransaction
import com.app.roomwithkotlincoroutine.db.entity.Discount
import com.app.roomwithkotlincoroutine.db.entity.Product
import com.app.roomwithkotlincoroutine.db.pojo.DemandWithProduct
import com.app.roomwithkotlincoroutine.db.pojo.ProductWithCoupon

interface DatabaseHelper {

    suspend fun getUsers(): List<Product>

    suspend fun getLastInsertedDiscount(): Discount

    suspend fun insertAll(users: List<Product>)

    suspend fun insert(users: Product)

    suspend fun insertDiscount(users: Discount): Long

    suspend fun insertDemand(users: Demand): Long

    suspend fun getProductWithDiscount(): List<ProductWithCoupon>

    suspend fun getAllDemands(): List<DemandWithProduct>

    suspend fun getDemandList(): List<DemandWithProduct>

    suspend fun insertTransaction(users: DemandTransaction)
}