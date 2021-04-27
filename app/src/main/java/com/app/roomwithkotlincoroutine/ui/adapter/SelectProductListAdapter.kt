package com.app.roomwithkotlincoroutine.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.roomwithkotlincoroutine.R
import com.app.roomwithkotlincoroutine.databinding.ItemProductBinding
import com.app.roomwithkotlincoroutine.db.pojo.ProductMuliSelect
import com.app.roomwithkotlincoroutine.util.AppConstant

class SelectProductListAdapter(private val itemClick: (ProductMuliSelect) -> Unit) :
    RecyclerView.Adapter<SelectProductListAdapter.HomeOffersViewHolder>() {

    private var list: ArrayList<ProductMuliSelect> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOffersViewHolder {

        val binding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeOffersViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: HomeOffersViewHolder, position: Int) {
        holder.bindForecast(list[position])
    }

    fun setItem(list: ArrayList<ProductMuliSelect>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun getList(): ArrayList<ProductMuliSelect> {
        return this.list
    }

    override fun getItemCount(): Int = list.size

    class HomeOffersViewHolder(
        private val binding: ItemProductBinding,
        private val itemClick: (ProductMuliSelect) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindForecast(
            firebaseMessageModel: ProductMuliSelect
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
                    //binding.tvDiscount.visibility = View.GONE
                    //binding.llDiscountDetail.visibility = View.VISIBLE

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

//                    binding.tvDiscountType.text =
//                        "Discount Type: " + firebaseMessageModel.type.toString() + ", "
//                    binding.tvDiscountAmount.text =
//                        "Amount: " + firebaseMessageModel.amount.toString()
                }

                binding.llProduct.setBackgroundColor(if (firebaseMessageModel.selected) Color.LTGRAY else Color.WHITE)

                itemView.setOnClickListener {
                    firebaseMessageModel.selected = (!firebaseMessageModel.selected)
                    binding.llProduct.setBackgroundColor(if (firebaseMessageModel.selected) Color.LTGRAY else Color.WHITE)
                    itemClick(this)
                }
            }
        }
    }
}