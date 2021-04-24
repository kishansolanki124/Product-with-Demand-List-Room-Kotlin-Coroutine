package com.app.roomwithkotlincoroutine.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.roomwithkotlincoroutine.db.DatabaseHelper
import com.app.roomwithkotlincoroutine.db.entity.Discount
import com.app.roomwithkotlincoroutine.db.entity.Product
import com.app.roomwithkotlincoroutine.db.pojo.ProductWithCoupon
import kotlinx.coroutines.launch

class RoomDBViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {

    private val productWithCoupon = MutableLiveData<List<ProductWithCoupon>>()

    init {
        fetchProductWithCoupon()
    }

    fun fetchProductWithCoupon() {
        viewModelScope.launch {
            val usersFromDb = dbHelper.getProductWithDiscount()
            productWithCoupon.postValue(usersFromDb)
        }
    }

    private fun addUser(product: Product) {
        viewModelScope.launch {
            dbHelper.insert(product)
            //users.postValue(Resource.success(usersFromDb))
        }
    }

    fun addDiscount(discount: Discount, title: String, description: String) {
        viewModelScope.launch {
            val id = dbHelper.insertDiscount(discount)
            addUser(
                Product(
                    name = title,
                    email = description,
                    avatar = "",
                    discountId = id.toInt()
                )
            )
        }
    }

    fun getProductWithCoupon(): LiveData<List<ProductWithCoupon>> {
        return productWithCoupon
    }
}