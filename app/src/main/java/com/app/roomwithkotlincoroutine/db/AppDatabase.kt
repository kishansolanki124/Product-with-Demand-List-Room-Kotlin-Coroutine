package com.app.roomwithkotlincoroutine.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.roomwithkotlincoroutine.db.dao.DemandDao
import com.app.roomwithkotlincoroutine.db.dao.DemandTransactionDao
import com.app.roomwithkotlincoroutine.db.dao.DiscountDao
import com.app.roomwithkotlincoroutine.db.dao.ProductDao
import com.app.roomwithkotlincoroutine.db.entity.Demand
import com.app.roomwithkotlincoroutine.db.entity.DemandTransaction
import com.app.roomwithkotlincoroutine.db.entity.Discount
import com.app.roomwithkotlincoroutine.db.entity.Product

@Database(
    entities = [Product::class, Discount::class, Demand::class, DemandTransaction::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    abstract fun discountDao(): DiscountDao

    abstract fun demandDao(): DemandDao

    abstract fun demandTransactionDao(): DemandTransactionDao
}