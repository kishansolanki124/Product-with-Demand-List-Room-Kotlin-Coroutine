package com.app.roomwithkotlincoroutine.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.roomwithkotlincoroutine.databinding.ItemSelectedProductsBinding
import com.app.roomwithkotlincoroutine.db.pojo.ProductMuliSelect

class SelectedProductListAdapter(
    private val itemClick: (ProductMuliSelect) -> Unit,
    private val deleteClick: (Int) -> Unit,
    private val updateTotal: (ProductMuliSelect) -> Unit
) :
    RecyclerView.Adapter<SelectedProductListAdapter.HomeOffersViewHolder>() {

    private var list: ArrayList<ProductMuliSelect> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOffersViewHolder {

        val binding =
            ItemSelectedProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeOffersViewHolder(binding, itemClick, deleteClick, updateTotal)
    }

    override fun onBindViewHolder(holder: HomeOffersViewHolder, position: Int) {
        holder.bindForecast(list[position])
    }

    fun removeItem(position: Int) {
        if (list.size > position && position != -1) {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun setItem(list: ArrayList<ProductMuliSelect>) {
        this.list.clear()
        for (item in list) {
            if (item.selected) {
                this.list.add(item)
            }
        }
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
        private val binding: ItemSelectedProductsBinding,
        private val itemClick: (ProductMuliSelect) -> Unit,
        private val deleteClick: (Int) -> Unit,
        private val updateTotal: (ProductMuliSelect) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindForecast(
            firebaseMessageModel: ProductMuliSelect
        ) {
            with(firebaseMessageModel) {
                binding.tvProductName.text = firebaseMessageModel.name
                binding.tvProductDescription.text = firebaseMessageModel.email
                binding.tvProductPrice.text = firebaseMessageModel.price.toString()
                binding.tvDiscountType.text =
                    firebaseMessageModel.type.toString() + firebaseMessageModel.amount.toString()

                //binding.llProduct.setBackgroundColor(if (firebaseMessageModel.selected) Color.LTGRAY else Color.WHITE)

                binding.ivDelete.setOnClickListener {
                    val position = adapterPosition
                    deleteClick(position)
                }

                binding.tvReduce.setOnClickListener {
                    if (binding.tvQuantity.text.toString().toInt() == 1) {
                        val position = adapterPosition
                        deleteClick(position)
                    } else {
                        binding.tvQuantity.text =
                            (binding.tvQuantity.text.toString().toInt() - 1).toString()
                        firebaseMessageModel.quantity = binding.tvQuantity.text.toString().toInt()
                    }
                    updateTotal(this)
                }

                binding.tvQuantity.text = firebaseMessageModel.quantity.toString()

                binding.tvAdd.setOnClickListener {
                    binding.tvQuantity.text =
                        (binding.tvQuantity.text.toString().toInt() + 1).toString()
                    firebaseMessageModel.quantity = binding.tvQuantity.text.toString().toInt()

                    updateTotal(this)
                }

                itemView.setOnClickListener {
                    itemClick(this)
                }
            }
        }
    }
}