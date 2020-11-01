package com.example.moonreadproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.confirm_item_layout.view.*
import kotlinx.android.synthetic.main.pro_item_layout.view.*

class ConfirmAdapter(val item: List<Cart> , context: Context): RecyclerView.Adapter<ConfirmAdapter.ViewHolder>() {

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val con_book_name = view.con_book_name
        val con_Qty = view.con_Qty
        val con_price = view.con_price
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.confirm_item_layout,parent,false)
        return ViewHolder(viewItem)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.con_book_name.text = item[position].cart_book_name
        holder.con_Qty.text = item[position].cart_qty.toString()
        holder.con_price.text = item[position].cart_price.toString()
    }

    override fun getItemCount(): Int {
        return item.size
    }

}