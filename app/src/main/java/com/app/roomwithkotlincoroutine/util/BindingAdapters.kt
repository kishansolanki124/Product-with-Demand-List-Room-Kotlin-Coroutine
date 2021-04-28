package com.app.roomwithkotlincoroutine.util

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.app.roomwithkotlincoroutine.R
import com.app.roomwithkotlincoroutine.db.pojo.ProductMuliSelect
import com.app.roomwithkotlincoroutine.db.pojo.ProductWithCoupon

object BindingAdapters {

    @SuppressLint("SetTextI18n")
    @BindingAdapter("bind:productDiscount")
    @JvmStatic
    fun discount(textView: TextView, productWithCoupon: ProductWithCoupon) {
        if (productWithCoupon.amount > 0) {
            if (productWithCoupon.type.equals(AppConstant.DiscountTYpe.AMOUNT)) {
                textView.text = "Discount: " + textView.context.getString(
                    R.string.rupee,
                    productWithCoupon.amount.toString()
                )
            } else {
                textView.text = "Discount: " + textView.context.getString(
                    R.string.percentage_x,
                    productWithCoupon.amount.toInt()
                )
            }
        } else {
            textView.text = "Discount: NA"
        }
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("bind:productDiscount")
    @JvmStatic
    fun discount(textView: TextView, productWithCoupon: ProductMuliSelect) {
        if (productWithCoupon.amount > 0) {
            if (productWithCoupon.type.equals(AppConstant.DiscountTYpe.AMOUNT)) {
                textView.text = "Discount: " + textView.context.getString(
                    R.string.rupee,
                    productWithCoupon.amount.toString()
                )
            } else {
                textView.text = "Discount: " + textView.context.getString(
                    R.string.percentage_x,
                    productWithCoupon.amount.toInt()
                )
            }
        } else {
            textView.text = "Discount: NA"
        }
    }
}