package com.example.moonreadproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.admin_order_layout.view.*


class MailAdapter(val item:List<Order> , context: Context):RecyclerView.Adapter<MailAdapter.ViewHolder>(){
    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val admin_orderID = view.admin_orderID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val adminMail = LayoutInflater.from(parent.context).inflate(R.layout.admin_order_layout,parent,false)
        val adminHolder = ViewHolder(adminMail)

        adminMail.setOnClickListener(){
            val pos = adminHolder.adapterPosition
            val context:Context = parent.context
            val order = item[pos]
            val intent = Intent(context,AdminEditStatusActivity::class.java)
            intent.putExtra("aOrderID",order.order_id.toString())
            intent.putExtra("aCustomerID",order.customer_id.toString())
            intent.putExtra("aTotal",order.total_price.toString())
            intent.putExtra("aDate",order.order_date)
            intent.putExtra("aAddress",order.address)
            intent.putExtra("aStatus",order.status)
            intent.putExtra("aPay",order.pay)
            intent.putExtra("aDel",order.delivery)
            context.startActivity(intent)
        }
        return adminHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.admin_orderID.text = "OrderID:"+item[position].order_id
    }

    override fun getItemCount(): Int {
        return item.size
    }
}