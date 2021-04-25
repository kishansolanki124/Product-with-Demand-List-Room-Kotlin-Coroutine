package com.app.roomwithkotlincoroutine.db.pojo

import java.io.Serializable

data class DemandWithProduct(
    val id: Int = 0,
    val total: Int = 0,
    val name: String?,
    val status: String?,
    val total_amount: Double,
    val total_discount: Double,
    val net_amount: Double,
    val created_date: Long,
    val updated_date: Long
) : Serializable

/**
total_amount, total_discount, net_amount, created_date, updated_date,
demandId, productId, quantity, total_amount, total_discount, net_amount, created_date, updated_date
 **/