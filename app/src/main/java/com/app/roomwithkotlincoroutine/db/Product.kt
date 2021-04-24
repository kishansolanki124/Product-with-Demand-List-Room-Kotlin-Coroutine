package com.app.roomwithkotlincoroutine.db

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    foreignKeys = [ForeignKey(
        entity = Discount::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("discountId"),
        onDelete = CASCADE
    )]
)
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "avatar") val avatar: String?,
    @ColumnInfo(name = "discountId") val discountId: Int
)

data class ProductWithCoupon(
    val id: Int = 0, val name: String?, val email: String?, val avatar: String?, val discountId: Int,
    val type: String?, val amount: Double?
)