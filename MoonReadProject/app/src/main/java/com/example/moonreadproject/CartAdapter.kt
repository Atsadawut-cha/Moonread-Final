package com.example.moonreadproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cart_item_layout.view.*
import kotlinx.android.synthetic.main.pro_item_layout.view.*

class CartAdapter(val item: List<Cart>, val context: Context?): RecyclerView.Adapter<CartAdapter.ViewHolder>(){

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val productName = view.ProductName
        val textPrice = view.text_price
        val textQTY = view.Tv_CartQTY

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val CartViewItem = LayoutInflater.from(parent.context).inflate(R.layout.cart_item_layout, parent, false)
        val myHolder3 =ViewHolder(CartViewItem)

        CartViewItem.setOnClickListener(){
            val pos = myHolder3.adapterPosition
            val context: Context = parent.context
            val cart = item[pos]
            val intent = Intent(context,EditDeleteOrderDetailActivity::class.java)
            intent.putExtra("mDetailID",cart.cart_orderDetail_id.toString())
            intent.putExtra("mOrder_id",cart.cart_order_id.toString())
            intent.putExtra("mBookName",cart.cart_book_name)
            intent.putExtra("mBookPrice",cart.cart_book_price.toString())
            intent.putExtra("mQty",cart.cart_qty.toString())
            intent.putExtra("mBookID",cart.cart_book_id.toString())
            context.startActivity(intent)
        }
        return myHolder3
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        holder.productName.text = item[position].cart_book_name
        holder.textQTY.text = item[position].cart_qty.toString()
        holder.textPrice.text = item[position].cart_price.toString()
    }

    override fun getItemCount(): Int {
        return item.size
    }

}