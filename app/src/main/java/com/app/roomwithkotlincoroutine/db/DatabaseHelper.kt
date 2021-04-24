package com.app.roomwithkotlincoroutine.db

interface DatabaseHelper {

    suspend fun getUsers(): List<Product>

    suspend fun getLastInsertedDiscount(): Discount

    suspend fun insertAll(users: List<Product>)

    suspend fun insert(users: Product)

    suspend fun insertDiscount(users: Discount): Long

    suspend fun getProductWithDiscount(): List<ProductWithCoupon>
}