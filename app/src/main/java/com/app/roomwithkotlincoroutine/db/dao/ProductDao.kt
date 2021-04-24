package com.app.roomwithkotlincoroutine.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.app.roomwithkotlincoroutine.db.entity.Product
import com.app.roomwithkotlincoroutine.db.pojo.ProductWithCoupon

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    suspend fun getAll(): List<Product>

    @Insert
    suspend fun insertAll(users: List<Product>)

    @Insert
    suspend fun insert(users: Product)

    @Delete
    suspend fun delete(user: Product)

    @Query("SELECT * FROM product join Discount on product.discountId = Discount.id ")
    suspend fun getProductWithDiscount(): List<ProductWithCoupon>
}