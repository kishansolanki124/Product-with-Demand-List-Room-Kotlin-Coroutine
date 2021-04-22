package com.app.roomwithkotlincoroutine.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.roomwithkotlincoroutine.R
import com.app.roomwithkotlincoroutine.db.Product

class ProductListAdapter(private val itemClick: (Product) -> Unit) :
    RecyclerView.Adapter<ProductListAdapter.HomeOffersViewHolder>() {

    private var list: ArrayList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOffersViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_vatan_nu_gham, parent, false)
        return HomeOffersViewHolder(
            view, itemClick
        )
    }

    override fun onBindViewHolder(holder: HomeOffersViewHolder, position: Int) {
        holder.bindForecast(list[position])
    }

    fun setItem(list: ArrayList<Product>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun reset() {
        this.list.clear()
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = list.size

    class HomeOffersViewHolder(
        view: View,
        private val itemClick: (Product) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        fun bindForecast(
            firebaseMessageModel: Product
        ) {
            with(firebaseMessageModel) {
//                Glide.with(itemView.ivVatan.context)
//                    .load(firebaseMessageModel.up_pro_img)
//                    .into(itemView.ivVatan)

                //itemView.tvVatanName.text = firebaseMessageModel.name

                itemView.setOnClickListener {
                    itemClick(this)
                }
            }
        }
    }
}