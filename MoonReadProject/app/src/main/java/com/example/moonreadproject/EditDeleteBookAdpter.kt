package com.example.moonreadproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.pro_item_layout.view.*

class EditDeleteBookAdpter(val items:List<Book>, val context: Context?):RecyclerView.Adapter<EditDeleteBookAdpter.ViewHolder>() {

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val book_name = view.tvTitle
        val imageBook = view.imageBook
        val book_price = view.tvPrice

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view_item = LayoutInflater.from(parent.context).inflate(R.layout.pro_item_layout, parent, false)
        val myHolder = ViewHolder(view_item)

        view_item.setOnClickListener(){
            val pos = myHolder.adapterPosition
            val context: Context = parent.context
            val book = items[pos]
            val intent = Intent(context, EditDeleteActivity::class.java)
            intent.putExtra("mId",book.book_id.toString())
            intent.putExtra("mName",book.book_name)
            intent.putExtra("mPrice",book.book_price.toString())
            intent.putExtra("mImg",book.img_book)
            intent.putExtra("mType",book.book_type)
            intent.putExtra("mDetail",book.book_detail)

            context.startActivity(intent)
        }

        return myHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.book_name?.text = items[position].book_name
        holder.book_price?.text = items[position].book_price.toString() +" บาท"
        if (context != null) {
            Glide.with(context)
                .load(items[position].img_book)
                .into(holder.imageBook)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}