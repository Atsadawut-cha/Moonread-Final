package com.example.moonreadproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.ad_ordetail_layout.view.*

class AdminORDetailAdapter(val item:List<Cart> ,context: Context):RecyclerView.Adapter<AdminORDetailAdapter.ViewHolder>() {

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val ad_book_name = view.ad_book_name
        val ad_Qty = view.ad_Qty
        val ad_price = view.ad_price
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val adOrItem = LayoutInflater.from(parent.context).inflate(R.layout.ad_ordetail_layout,parent,false)
        return ViewHolder(adOrItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ad_book_name.text = item[position].cart_book_name
        holder.ad_Qty.text = item[position].cart_qty.toString()
        holder.ad_price.text = item[position].cart_price.toString()
    }

    override fun getItemCount(): Int {
        return item.size
    }
}