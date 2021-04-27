package com.app.roomwithkotlincoroutine.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.roomwithkotlincoroutine.databinding.ItemDemandBinding
import com.app.roomwithkotlincoroutine.db.pojo.DemandWithProduct
import com.app.roomwithkotlincoroutine.util.getDate

class DemandListAdapter(private val itemClick: (DemandWithProduct) -> Unit) :
    RecyclerView.Adapter<DemandListAdapter.HomeOffersViewHolder>() {

    private var list: ArrayList<DemandWithProduct> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOffersViewHolder {
        val binding =
            ItemDemandBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeOffersViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: HomeOffersViewHolder, position: Int) {
        holder.bindForecast(list[position])
    }

    fun setItem(list: ArrayList<DemandWithProduct>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    class HomeOffersViewHolder(
        private val binding: ItemDemandBinding,
        private val itemClick: (DemandWithProduct) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindForecast(
            firebaseMessageModel: DemandWithProduct
        ) {
            with(firebaseMessageModel) {
                binding.tvPartyName.text = "Party Name: " + firebaseMessageModel.name
                binding.tvStatus.text = "Status: " + firebaseMessageModel.status

                binding.tvCreatedDate.text = "Created Date: " + getDate(
                    firebaseMessageModel.created_date,
                    "d MMM yyyy, hh:mm aaa"
                )

                binding.tvUpdatedDate.text =
                    "Updated Date: " + getDate(
                        firebaseMessageModel.updated_date,
                        "d MMM yyyy, hh:mm aaa"
                    )
                binding.tvTotalProducts.text = "Total Products: ${firebaseMessageModel.total}"
                binding.tvAmount.text = "Amount:\n" + firebaseMessageModel.total_amount.toString()
                binding.tvDiscount.text =
                    "Discount:\n" + firebaseMessageModel.total_discount.toString()
                binding.tvNetAmount.text =
                    "Net Amount:\n" + firebaseMessageModel.net_amount.toString()

                binding.ivEdit.setOnClickListener {
                    itemClick(this)
                }
            }
        }
    }
}