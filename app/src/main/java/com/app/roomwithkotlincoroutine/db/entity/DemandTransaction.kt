package com.app.roomwithkotlincoroutine.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = Demand::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("demandId"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Product::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("productId"),
        onDelete = ForeignKey.CASCADE
    )]
)

data class DemandTransaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "demandId") val demandId: Int,
    @ColumnInfo(name = "productId") val productId: Int,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "total_amount") val totalAmount: Double,
    @ColumnInfo(name = "total_discount") val totalDiscount: Double,
    @ColumnInfo(name = "net_amount") val netAmount: Double,
    @ColumnInfo(name = "created_date") val createdDate: Long,
    @ColumnInfo(name = "updated_date") val updatedDate: Long
)