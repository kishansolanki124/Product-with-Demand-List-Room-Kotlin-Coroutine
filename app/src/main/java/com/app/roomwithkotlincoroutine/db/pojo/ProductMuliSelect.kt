package com.app.roomwithkotlincoroutine.db.pojo

import androidx.room.Ignore
import java.io.Serializable

data class ProductMuliSelect(
    val id: Int = 0,
    val name: String?,
    val description: String?,
    val discountId: Int,
    val type: String?,
    val amount: Double,
    var quantity: Int = 1,
    var price: Double,
    var selected: Boolean = false
) : Serializable