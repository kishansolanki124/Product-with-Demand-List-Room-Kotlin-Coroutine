package com.app.roomwithkotlincoroutine.db.pojo

import java.io.Serializable

data class DemandTransactionAndProductMultiSelect(
    val id: Int = 0,
    val demandId: Int,
    val productId: Int,
    val total_amount: Double,
    val total_discount: Double,
    val net_amount: Double,
    val created_date: Long,
    val updated_date: Long,
    val name: String?,
    val description: String?,
    val discountId: Int,
    val type: String?,
    val amount: Double,
    var quantity: Int = 1,
    var price: Double
) : Serializable