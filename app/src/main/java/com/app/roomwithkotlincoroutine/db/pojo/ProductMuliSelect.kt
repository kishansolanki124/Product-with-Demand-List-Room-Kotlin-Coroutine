package com.app.roomwithkotlincoroutine.db.pojo

import java.io.Serializable

data class ProductMuliSelect(
    val id: Int = 0,
    val name: String?,
    val email: String?,
    val avatar: String?,
    val discountId: Int,
    val type: String?,
    val amount: Double,
    var quantity: Int = 1,
    var price: Double,
    var selected: Boolean = false
) : Serializable