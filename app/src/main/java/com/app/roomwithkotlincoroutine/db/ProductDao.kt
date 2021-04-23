package com.app.roomwithkotlincoroutine.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

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
}