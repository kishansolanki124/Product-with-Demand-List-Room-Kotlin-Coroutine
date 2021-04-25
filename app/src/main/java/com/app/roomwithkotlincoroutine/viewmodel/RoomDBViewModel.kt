package com.app.roomwithkotlincoroutine.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.roomwithkotlincoroutine.db.DatabaseHelper
import com.app.roomwithkotlincoroutine.db.entity.Demand
import com.app.roomwithkotlincoroutine.db.entity.DemandTransaction
import com.app.roomwithkotlincoroutine.db.entity.Discount
import com.app.roomwithkotlincoroutine.db.entity.Product
import com.app.roomwithkotlincoroutine.db.pojo.DemandWithProduct
import com.app.roomwithkotlincoroutine.db.pojo.ProductMuliSelect
import com.app.roomwithkotlincoroutine.db.pojo.ProductWithCoupon
import kotlinx.coroutines.launch

class RoomDBViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {

    private val productWithCoupon = MutableLiveData<List<ProductWithCoupon>>()
    private val demandList = MutableLiveData<List<DemandWithProduct>>()

    init {
        fetchProductWithCoupon()
    }

    fun fetchProductWithCoupon() {
        viewModelScope.launch {
            val usersFromDb = dbHelper.getProductWithDiscount()
            productWithCoupon.postValue(usersFromDb)
        }
    }

    fun fetchDemandList() {
        viewModelScope.launch {
            val usersFromDb = dbHelper.getDemandList()
            demandList.postValue(usersFromDb)
        }
    }

    private fun addUser(product: Product) {
        viewModelScope.launch {
            dbHelper.insert(product)
            //users.postValue(Resource.success(usersFromDb))
        }
    }

    fun addDemand(demand: Demand, productList: ArrayList<ProductMuliSelect>) {
        viewModelScope.launch {
            val id = dbHelper.insertDemand(demand)
            for (item in productList) {
                var amount = 0.0
                var discountAMount = 0.0
                var netAMount = 0.0
                when {
                    item.type.equals("none") -> {
                        amount += (item.price * item.quantity)
                        netAMount += (item.price * item.quantity)
                    }
                    item.type.equals("percentage") -> {
                        amount += (item.price * item.quantity)

                        val percentageAmount = (item.price * item.amount) / 100
                        discountAMount += (percentageAmount * item.quantity)

                        val netPayableAmount = item.price - percentageAmount
                        netAMount += (netPayableAmount * item.quantity)
                    }
                    item.type.equals("amount") -> {
                        amount += (item.price * item.quantity)

                        discountAMount += (item.amount * item.quantity)
                        netAMount += ((item.price - item.amount) * item.quantity)
                    }
                }

                val demandTransaction = DemandTransaction(
                    demandId = id.toInt(),
                    productId = item.id,
                    totalAmount = amount,
                    quantity = item.quantity,
                    totalDiscount = discountAMount,
                    netAmount = netAMount,
                    createdDate = demand.createdDate,
                    updatedDate = demand.updatedDate
                )
                dbHelper.insertTransaction(demandTransaction)
            }
        }
    }

    fun addDiscount(discount: Discount, title: String, description: String, price: Double) {
        viewModelScope.launch {
            val id = dbHelper.insertDiscount(discount)
            addUser(
                Product(
                    name = title,
                    email = description,
                    avatar = "",
                    price = price,
                    discountId = id.toInt()
                )
            )
        }
    }

    fun getProductWithCoupon(): LiveData<List<ProductWithCoupon>> {
        return productWithCoupon
    }

    fun getDemandList(): LiveData<List<DemandWithProduct>> {
        return demandList
    }
}