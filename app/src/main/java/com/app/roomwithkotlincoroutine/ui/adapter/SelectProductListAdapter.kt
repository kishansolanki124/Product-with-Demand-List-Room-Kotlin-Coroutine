package com.app.roomwithkotlincoroutine.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.roomwithkotlincoroutine.databinding.ItemVatanNuGhamBinding
import com.app.roomwithkotlincoroutine.db.pojo.ProductMuliSelect

class SelectProductListAdapter(private val itemClick: (ProductMuliSelect) -> Unit) :
    RecyclerView.Adapter<SelectProductListAdapter.HomeOffersViewHolder>() {

    private var list: ArrayList<ProductMuliSelect> = ArrayList()

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

    fun setItem(list: ArrayList<ProductMuliSelect>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun getList(): ArrayList<ProductMuliSelect> {
        return this.list
    }

    fun reset() {
        this.list.clear()
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = list.size

    class HomeOffersViewHolder(
        private val binding: ItemVatanNuGhamBinding,
        private val itemClick: (ProductMuliSelect) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindForecast(
            firebaseMessageModel: ProductMuliSelect
        ) {
            with(firebaseMessageModel) {
//                Glide.with(itemView.ivVatan.context)
//                    .load(firebaseMessageModel.up_pro_img)
//                    .into(itemView.ivVatan)

                binding.tvProductName.text = firebaseMessageModel.name
                binding.tvProductDescription.text = firebaseMessageModel.email
                binding.tvDiscountType.text =
                    firebaseMessageModel.type.toString() + firebaseMessageModel.amount.toString()

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