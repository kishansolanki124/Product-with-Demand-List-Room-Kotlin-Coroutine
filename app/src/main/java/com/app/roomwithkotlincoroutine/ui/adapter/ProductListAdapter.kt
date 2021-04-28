package com.app.roomwithkotlincoroutine.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.roomwithkotlincoroutine.databinding.ItemProductBinding
import com.app.roomwithkotlincoroutine.db.pojo.ProductWithCoupon

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
        fun bindForecast(
            firebaseMessageModel: ProductWithCoupon
        ) {
            with(firebaseMessageModel) {

                binding.product = firebaseMessageModel

                itemView.setOnClickListener {
                    itemClick(this)
                }
            }
        }
    }
}