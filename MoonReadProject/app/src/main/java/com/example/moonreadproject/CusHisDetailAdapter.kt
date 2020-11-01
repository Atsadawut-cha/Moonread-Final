package com.example.moonreadproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.his_detail_layout.view.*

class CusHisDetailAdapter(val item:List<Cart> , context: Context):RecyclerView.Adapter<CusHisDetailAdapter.ViewHolder>() {

    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val his_book_name = view.his_book_name
        val his_Qty = view.his_Qty
        val his_price = view.his_price
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val hisItem = LayoutInflater.from(parent.context).inflate(R.layout.his_detail_layout,parent,false)
        return ViewHolder(hisItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.his_book_name.text = item[position].cart_book_name
        holder.his_Qty.text =  item[position].cart_qty.toString()
        holder.his_price.text = item[position].cart_price.toString()
    }

    override fun getItemCount(): Int {
        return item.size
    }
}