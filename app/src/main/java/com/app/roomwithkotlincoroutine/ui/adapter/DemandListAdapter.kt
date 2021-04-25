package com.app.roomwithkotlincoroutine.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.roomwithkotlincoroutine.databinding.ItemDemandBinding
import com.app.roomwithkotlincoroutine.db.entity.Demand
import com.app.roomwithkotlincoroutine.db.pojo.DemandWithProduct

class DemandListAdapter(private val itemClick: (DemandWithProduct) -> Unit) :
    RecyclerView.Adapter<DemandListAdapter.HomeOffersViewHolder>() {

    private var list: ArrayList<DemandWithProduct> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOffersViewHolder {

        val binding =
            ItemDemandBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeOffersViewHolder(binding, itemClick)

//        val view =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_vatan_nu_gham, parent, false)
//        return HomeOffersViewHolder(binding, itemClick
//        )
    }

    override fun onBindViewHolder(holder: HomeOffersViewHolder, position: Int) {
        holder.bindForecast(list[position])
    }

    fun setItem(list: ArrayList<DemandWithProduct>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun reset() {
        this.list.clear()
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = list.size

    class HomeOffersViewHolder(
        private val binding: ItemDemandBinding,
        private val itemClick: (DemandWithProduct) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindForecast(
            firebaseMessageModel: DemandWithProduct
        ) {
            with(firebaseMessageModel) {
//                Glide.with(itemView.ivVatan.context)
//                    .load(firebaseMessageModel.up_pro_img)
//                    .into(itemView.ivVatan)

                binding.tvAmount.text = firebaseMessageModel.total_amount.toString()
                binding.tvCreatedDate.text = firebaseMessageModel.created_date.toString()
                binding.tvDiscount.text = firebaseMessageModel.total_discount.toString()
                binding.tvNetAmount.text = firebaseMessageModel.net_amount.toString()
                binding.tvPartyName.text = firebaseMessageModel.name
                binding.tvStatus.text = firebaseMessageModel.status
                binding.tvTotalProducts.text = "Total Products: ${firebaseMessageModel.total}"
                binding.tvUpdatedDate.text = firebaseMessageModel.updated_date.toString()

                itemView.setOnClickListener {
                    itemClick(this)
                }
            }
        }
    }
}