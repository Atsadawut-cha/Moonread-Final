package com.example.moonreadproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.hsitory_item_layout.view.*

class CusHistoryAdapter(val item:List<Order> , context: Context?):RecyclerView.Adapter<CusHistoryAdapter.ViewHolder>() {
    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val hisOrderID = view.text_cus_orderID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val hisItem = LayoutInflater.from(parent.context).inflate(R.layout.hsitory_item_layout,parent,false)
        val myHisholder = ViewHolder(hisItem)

        hisItem.setOnClickListener(){
            val pos = myHisholder.adapterPosition
            val context:Context = parent.context
            val order = item[pos]
            val intent = Intent(context,CusOrderDetailActivity::class.java)
            intent.putExtra("mOrder_id",order.order_id.toString())
            intent.putExtra("mCus_id",order.customer_id.toString())
            intent.putExtra("mTotal",order.total_price.toString())
            intent.putExtra("mOrder_date",order.order_date)
            intent.putExtra("mAddress",order.address)
            intent.putExtra("mStatus",order.status)
            intent.putExtra("mPay",order.pay)
            intent.putExtra("mDelivery",order.delivery)
            context.startActivity(intent)
        }

        return myHisholder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.hisOrderID.text = "รหัสการสั่งซื้อที่: "+item[position].order_id
    }

    override fun getItemCount(): Int {
        return item.size
    }
}