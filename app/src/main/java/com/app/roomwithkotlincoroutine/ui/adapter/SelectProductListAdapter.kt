package com.app.roomwithkotlincoroutine.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.roomwithkotlincoroutine.databinding.ItemSelectProductBinding
import com.app.roomwithkotlincoroutine.db.pojo.ProductMuliSelect

class SelectProductListAdapter(private val itemClick: (ProductMuliSelect) -> Unit) :
    RecyclerView.Adapter<SelectProductListAdapter.HomeOffersViewHolder>() {

    private var list: ArrayList<ProductMuliSelect> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOffersViewHolder {

        val binding =
            ItemSelectProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        private val binding: ItemSelectProductBinding,
        private val itemClick: (ProductMuliSelect) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindForecast(
            firebaseMessageModel: ProductMuliSelect
        ) {
            with(firebaseMessageModel) {
                binding.product = firebaseMessageModel

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