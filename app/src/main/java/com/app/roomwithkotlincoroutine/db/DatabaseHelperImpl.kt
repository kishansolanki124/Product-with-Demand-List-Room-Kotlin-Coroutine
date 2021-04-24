package com.app.roomwithkotlincoroutine.db

import com.app.roomwithkotlincoroutine.db.entity.Discount
import com.app.roomwithkotlincoroutine.db.entity.Product
import com.app.roomwithkotlincoroutine.db.pojo.ProductWithCoupon

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getUsers(): List<Product> = appDatabase.productDao().getAll()

    override suspend fun insertAll(users: List<Product>) = appDatabase.productDao().insertAll(users)

    override suspend fun getLastInsertedDiscount() =
        appDatabase.discountDao().getLastInsertedValue()

    override suspend fun insert(users: Product) = appDatabase.productDao().insert(users)

    override suspend fun insertDiscount(users: Discount): Long =
        appDatabase.discountDao().insert(users)

    override suspend fun getProductWithDiscount(): List<ProductWithCoupon> =
        appDatabase.productDao().getProductWithDiscount()
}