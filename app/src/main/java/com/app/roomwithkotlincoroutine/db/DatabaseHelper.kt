package com.app.roomwithkotlincoroutine.db

import com.app.roomwithkotlincoroutine.db.entity.Discount
import com.app.roomwithkotlincoroutine.db.entity.Product
import com.app.roomwithkotlincoroutine.db.pojo.ProductWithCoupon

interface DatabaseHelper {

    suspend fun getUsers(): List<Product>

    suspend fun getLastInsertedDiscount(): Discount

    suspend fun insertAll(users: List<Product>)

    suspend fun insert(users: Product)

    suspend fun insertDiscount(users: Discount): Long

    suspend fun getProductWithDiscount(): List<ProductWithCoupon>
}