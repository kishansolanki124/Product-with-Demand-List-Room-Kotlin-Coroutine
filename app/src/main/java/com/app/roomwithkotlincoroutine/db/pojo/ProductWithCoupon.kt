package com.app.roomwithkotlincoroutine.db.pojo

data class ProductWithCoupon(
    val id: Int = 0,
    val name: String?,
    val description: String?,
    val discountId: Int,
    val type: String?,
    val amount: Double,
    val price: Double
)