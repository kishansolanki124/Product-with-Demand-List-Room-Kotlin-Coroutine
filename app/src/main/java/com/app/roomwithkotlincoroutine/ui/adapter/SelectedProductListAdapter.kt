package com.app.roomwithkotlincoroutine.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.roomwithkotlincoroutine.R
import com.app.roomwithkotlincoroutine.databinding.ItemSelectedProductsBinding
import com.app.roomwithkotlincoroutine.db.pojo.ProductMuliSelect
import com.app.roomwithkotlincoroutine.util.AppConstant

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

    override fun getItemCount(): Int = list.size

    class HomeOffersViewHolder(
        private val binding: ItemSelectedProductsBinding,
        private val itemClick: (ProductMuliSelect) -> Unit,
        private val deleteClick: (Int) -> Unit,
        private val updateTotal: (ProductMuliSelect) -> Unit
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

                binding.tvQuantity.text = firebaseMessageModel.quantity.toString()

                if (firebaseMessageModel.amount <= 0) {
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