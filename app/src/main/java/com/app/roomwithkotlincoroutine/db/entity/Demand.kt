package com.app.roomwithkotlincoroutine.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity()
data class Demand(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "status") val status: String?,
    @ColumnInfo(name = "total_amount") val totalAmount: Double,
    @ColumnInfo(name = "total_discount") val totalDiscount: Double,
    @ColumnInfo(name = "net_amount") val netAmount: Double,
    @ColumnInfo(name = "created_date") val createdDate: Long,
    @ColumnInfo(name = "updated_date") val updatedDate: Long
)