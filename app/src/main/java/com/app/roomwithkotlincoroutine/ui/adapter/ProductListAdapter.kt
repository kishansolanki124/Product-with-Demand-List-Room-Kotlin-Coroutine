package com.app.roomwithkotlincoroutine.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.roomwithkotlincoroutine.R
import com.app.roomwithkotlincoroutine.databinding.ItemProductBinding
import com.app.roomwithkotlincoroutine.db.pojo.ProductWithCoupon
import com.app.roomwithkotlincoroutine.util.AppConstant

class ProductListAdapter(private val itemClick: (ProductWithCoupon) -> Unit) :
    RecyclerView.Adapter<ProductListAdapter.HomeOffersViewHolder>() {

    private var list: ArrayList<ProductWithCoupon> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOffersViewHolder {
        val binding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeOffersViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: HomeOffersViewHolder, position: Int) {
        holder.bindForecast(list[position])
    }

    fun setItem(list: ArrayList<ProductWithCoupon>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    class HomeOffersViewHolder(
        private val binding: ItemProductBinding,
        private val itemClick: (ProductWithCoupon) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindForecast(
            firebaseMessageModel: ProductWithCoupon
        ) {
            with(firebaseMessageModel) {
                binding.tvProductName.text = "Name: " + firebaseMessageModel.name
                binding.tvProductDescription.text =
                    "Description: " + firebaseMessageModel.description
                binding.tvProductPrice.text = "Price: " + binding.tvProductPrice.context.getString(
                    R.string.rupee,
                    firebaseMessageModel.price.toString()
                )

                if (firebaseMessageModel.amount <= 0) {
                    //binding.llDiscountDetail.visibility = View.GONE
                    //binding.tvDiscount.visibility = View.VISIBLE
                    binding.tvDiscount.text = "Discount: NA"
                } else {
                    if (firebaseMessageModel.type.toString()
                        == (AppConstant.DiscountTYpe.AMOUNT)
                    ) {
                        binding.tvDiscount.text =
                            "Discount: " + binding.tvDiscount.context.getString(
                                R.string.rupee,
                                firebaseMessageModel.amount.toString()
                            )
                    } else {
                        binding.tvDiscount.text =
                            "Discount: " + binding.tvDiscount.context.getString(
                                R.string.percentage_x,
                                firebaseMessageModel.amount.toInt()
                            )
                    }
                }

                itemView.setOnClickListener {
                    itemClick(this)
                }
            }
        }
    }
}