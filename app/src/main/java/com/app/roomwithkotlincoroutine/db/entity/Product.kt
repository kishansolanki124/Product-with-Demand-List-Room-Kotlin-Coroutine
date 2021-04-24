package com.app.roomwithkotlincoroutine.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

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