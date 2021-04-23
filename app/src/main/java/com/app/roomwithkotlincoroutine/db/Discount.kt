package com.app.roomwithkotlincoroutine.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Discount(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "amount") val amount: Double?
)
/**
none, percentage, fix
 **/