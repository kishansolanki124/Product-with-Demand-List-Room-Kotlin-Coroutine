package com.app.roomwithkotlincoroutine.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Product::class, Discount::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun discountDao(): DiscountDao
}