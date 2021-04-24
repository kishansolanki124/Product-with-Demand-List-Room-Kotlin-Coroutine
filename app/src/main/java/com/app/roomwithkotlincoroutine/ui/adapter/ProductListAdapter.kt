package com.app.roomwithkotlincoroutine.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.roomwithkotlincoroutine.databinding.ItemVatanNuGhamBinding
import com.app.roomwithkotlincoroutine.db.pojo.ProductWithCoupon

class ProductListAdapter(private val itemClick: (ProductWithCoupon) -> Unit) :
    RecyclerView.Adapter<ProductListAdapter.HomeOffersViewHolder>() {

    private var list: ArrayList<ProductWithCoupon> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOffersViewHolder {

        val binding =
            ItemVatanNuGhamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeOffersViewHolder(binding, itemClick)

//        val view =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_vatan_nu_gham, parent, false)
//        return HomeOffersViewHolder(binding, itemClick
//        )
    }

    override fun onBindViewHolder(holder: HomeOffersViewHolder, position: Int) {
        holder.bindForecast(list[position])
    }

    fun setItem(list: ArrayList<ProductWithCoupon>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun reset() {
        this.list.clear()
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = list.size

    class HomeOffersViewHolder(
        private val binding: ItemVatanNuGhamBinding,
        private val itemClick: (ProductWithCoupon) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindForecast(
            firebaseMessageModel: ProductWithCoupon
        ) {
            with(firebaseMessageModel) {
//                Glide.with(itemView.ivVatan.context)
//                    .load(firebaseMessageModel.up_pro_img)
//                    .into(itemView.ivVatan)

                binding.tvProductName.text = firebaseMessageModel.name
                binding.tvProductDescription.text = firebaseMessageModel.email
                binding.tvProductPrice.text = firebaseMessageModel.price.toString()
                binding.tvDiscountType.text =
                    firebaseMessageModel.type.toString() + firebaseMessageModel.amount.toString()

                itemView.setOnClickListener {
                    itemClick(this)
                }
            }
        }
    }
}